#|
Licensed under 3-clause BSD License:
Copyright © 2009, Mathieu Lemoine
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
 * Neither the name of Mathieu Lemoine nor the
   names of contributors may be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
\(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION\) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
\(INCLUDING NEGLIGENCE OR OTHERWISE\) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
|#

(cl:defpackage #:meta-model-generation
  (:use #:cl #:code-generation #:restricted-list #:xt-pattern)
  (:import-from #:alexandria
                #:with-gensyms
                #:circular-list #:mappend
                #:rcurry #:curry
                #:disjoin #:conjoin
                #:compose) 
  (:export #:flatten

           #:primitive-type-p #:constituent-p

           #:*model-symbol* #:*constituents* #:*factory* #:*visitor* #:*kind-enum*
           #:*model-package*

           #:defMMfun

           #:get-enum-constant #:get-enum-constants

           #:extract-kind #:generate-constituent-class-name #:generate-constituent-implementation-class-name
           #:resolve-constituent-class #:get-constituent-class-import
           #:get-constituent-field-class #:resolve-constituent-field-class

           #:*method-generators* #:defgenerator #:generate-method-implementation

           #:mm-type #:create-mm-type #:copy-mm-type #:mm-type-p #:mm-type->java-type
           #:java-type->mm-type #:mm-type-package #:mm-type-tag #:mm-type-name
           #:mm-type-generics #:mm-type-enclosing-type

           #:*path-to-helpers*
           #:mm-constituent #:create-mm-constituent #:create-mm-constituent-w/impl-file-helper
           #:create-mm-constituent-w/impl-only-generics #:create-mm-constituent-full
           #:copy-mm-constituent #:mm-constituent-p #:mm-constituent-name #:mm-constituent-generics
           #:mm-constituent-create-method
           #:mm-constituent-implementation-generics #:mm-constituent-extends
           #:mm-constituent-implements #:mm-constituent-interface-imports
           #:mm-constituent-implementation-imports #:mm-constituent-created #:mm-constituent-author
           #:mm-constituent-description-lines #:mm-constituent-fields #:mm-constituent-methods
           #:mm-constituent-flags #:mm-constituent-impl-file-helper
           #:mm-constituent-implementation-generics

           #:mm-field #:create-mm-field #:create-mm-field-w/default #:copy-mm-field #:mm-field-p
           #:mm-field-name #:mm-field-type #:mm-field-flags #:mm-field-default

           #:mm-factory #:create-mm-factory #:copy-mm-factory #:mm-factory-p #:mm-factory-name
           #:mm-factory-generics #:mm-factory-interface-imports #:mm-factory-implementation-imports
           #:mm-factory-created #:mm-factory-author #:mm-factory-description-lines
           #:mm-factory-skip-list #:mm-factory-fields #:mm-factory-methods #:mm-factory-impl-file-helper

           #:mm-visitor #:create-mm-visitor #:copy-mm-visitor #:mm-visitor-p #:mm-visitor-name
           #:mm-visitor-generics #:mm-visitor-imports #:mm-visitor-created #:mm-visitor-author
           #:mm-visitor-description-lines #:mm-visitor-skip-list #:mm-visitor-methods

           #:generate-set-getter-exceptions #:generate-list-getter-exceptions
           #:generate-atom-setter-exceptions #:generate-atom-getter-exceptions
           #:generate-set-remover-exceptions #:generate-set-adder-exceptions
           #:generate-list-remover-exceptions #:generate-list-adder-exceptions
           #:generate-set-remover-constituent-type #:generate-list-remover-name
           #:generate-set-getter-name #:%generate-constituent-create-method
           #:%generate-constituent-create-method-name #:%generate-constituent-create-method-exceptions
           #:generate-set-adder-name #:%generate-create-method #:generate-list-adder-constituent-type
           #:generate-list-adder-constituent-id-extractor
           #:generate-set-remover-constituent-id-extractor #:generate-atom-setter-name
           #:generate-list-remover-constituent-type #:generate-list-remover-constituent-id-extractor
           #:generate-set-adder-constituent-id-extractor #:generate-list-getter-name
           #:generate-set-remover-name #:generate-atom-getter-name
           #:generate-set-adder-constituent-type #:generate-list-adder-name
 
           #:generate-atom-field-initialization
           #:generate-list-field-initialization #:generate-set-field-initialization

           #:generate-create-arguments-from-constructor #:generate-in-create-constructor-agruments
           #:generate-factory-create-method-body

           #:generate-constituent-interface-imports #:generate-constituent-implementation-imports

           #:generate-actual-enum-kind #:generate-enum-kind-constants
           #:generate-constituent-interface #:generate-constituent-implementation
           #:generate-factory-interface #:generate-factory-implementation
           #:generate-visitor-interface))

(cl:in-package #:meta-model-generation)

;; define structures
(eval-when (:compile-toplevel :load-toplevel :execute)
  (deftype-list-of string)
  (deftype-list-of (list-of string))
  (defstruct (mm-type
               (:constructor create-mm-type
                             (package/tag name &optional generics enclosing-type)))
    (package/tag nil :type (or symbol (or string null)))
    (name nil :type string)
    (generics () :type (list-of (list-of string)))
    (enclosing-type () :type (or mm-type null)))

  (set-dispatch-macro-character #\# #\T
                                (lambda (stream subchar arg)
                                  (declare (ignore subchar arg))
                                  `(create-mm-type ,@(read stream t nil t))))

  (deftype-list-of mm-type)
  (deftype-list-of java-import)
  (deftype-list-of (or string null))
  (deftype-list-of mm-field)
  (deftype-list-of java-method)
  (deftype-list-of keyword)
  (defstruct (mm-constituent
               (:constructor create-mm-constituent
                             (name generics extends implements interface-imports
                                   implementation-imports created author description-lines
                                   fields methods &rest flags
                                   &aux (implementation-generics generics)))
               (:constructor create-mm-constituent-w/impl-only-generics
                             (name generics implementation-generics extends implements
                                   interface-imports implementation-imports created author
                                   description-lines fields methods &rest flags))
               (:constructor create-mm-constituent-w/impl-file-helper*
                             (name generics extends implements interface-imports
                                   implementation-imports created author description-lines
                                   fields methods impl-file-helper flags
                                   &aux (implementation-generics generics)))
               (:constructor create-mm-constituent-full*
                             (name generics implementation-generics extends implements
                                   interface-imports implementation-imports created author
                                   description-lines fields methods impl-file-helper flags)))
    (name "" :type string)
    (create-method (%generate-constituent-create-method
                    name generics
                    (find-method-by-name (generate-constituent-implementation-class-name name)
                                         methods))
                   :type (or java-method null))
    (generics () :type (list-of (list-of string)))
    (implementation-generics () :type (list-of (list-of string)))
    (extends nil :type (or mm-type null))
    (implements () :type (list-of mm-type))
    (interface-imports () :type (list-of java-import))
    (implementation-imports () :type (list-of java-import))
    (created (generation-date) :type string)
    (author "" :type string)
    (description-lines () :type (list-of (or string null)))
    (fields () :type (list-of mm-field))
    (methods () :type (list-of java-method))
    (flags () :type (list-of keyword))
    (impl-file-helper nil :type (or null string pathname file-stream)))

  (defstruct (mm-field
               (:constructor create-mm-field
                             (name type &rest flags))
               (:constructor create-mm-field-w/default
                             (name type default &rest flags)))
    (name "" :type string)
    (type "" :type mm-type)
    (default nil :type (or string null))
    (flags () :type (list-of keyword)))

  (defstruct (mm-factory
               (:constructor create-mm-factory*
                             (name generics interface-imports implementation-imports
                                   created author description-lines
                                   &optional skip-list fields methods impl-file-helper)))
    (name "" :type string)
    (generics () :type (list-of (list-of string)))
    (interface-imports () :type (list-of java-import))
    (implementation-imports () :type (list-of java-import))
    (created (generation-date) :type string)
    (author "" :type string)
    (description-lines () :type (list-of (or string null)))
    (skip-list () :type (list-of string))
    (fields () :type (list-of mm-field))
    (methods () :type (list-of java-method))
    (impl-file-helper nil :type (or null string pathname file-stream)))

  (defstruct (mm-visitor
               (:constructor create-mm-visitor
                             (name generics imports created author description-lines
                                   &optional skip-list methods)))
    (name "" :type string)
    (generics () :type (list-of (list-of string)))
    (imports () :type (list-of java-import))
    (created (generation-date) :type string)
    (author "" :type string)
    (description-lines () :type (list-of (or string null)))
    (skip-list () :type (list-of string))
    (methods () :type (list-of java-method))))

(deftype-list-of java-generic)

(declaim (type string *path-to-helpers*))
(defvar *path-to-helpers*)

(declaim (ftype (function (mm-constituent)
                          (values (or null java-method) &optional))
                mm-constituent-constructor))
(defun mm-constituent-constructor (constituent)
  (find-method-by-name
   (generate-constituent-implementation-class-name (mm-constituent-name constituent))
   (mm-constituent-methods constituent)))

(declaim (ftype (function (string (list-of java-method) &optional function)
                          (values (or null java-method) &optional))
                find-method-by-name))
(defun find-method-by-name (name methods &optional (test #'string=))
  (car (member name methods
               :key (compose #'java-method-name #'java-method-signature)
               :test test)))

(declaim (ftype (function (string
                           (list-of (list-of string))
                           (or mm-type null)
                           (list-of mm-type)
                           (list-of java-import)
                           (list-of java-import)
                           string string
                           (list-of (or string null))
                           (list-of mm-field)
                           (list-of java-method)
                           (or null string pathname file-stream)
                           &rest keyword))))
(defun create-mm-constituent-w/impl-file-helper
    (name generics extends implements interface-imports
     implementation-imports created author description-lines
     fields methods impl-file-helper &rest flags)
  (create-mm-constituent-w/impl-file-helper* name generics
                                             extends implements
                                             interface-imports implementation-imports
                                             created author
                                             description-lines
                                             fields methods
                                             (when impl-file-helper
                                               (format nil "~A~A" *path-to-helpers* impl-file-helper))
                                             flags))

(declaim (ftype (function (string
                           (list-of (list-of string))
                           (list-of (list-of string))
                           (or mm-type null)
                           (list-of mm-type)
                           (list-of java-import)
                           (list-of java-import)
                           string string
                           (list-of (or string null))
                           (list-of mm-field)
                           (list-of java-method)
                           (or null string pathname file-stream)
                           &rest keyword)
                          (values mm-constituent &optional))
                create-mm-constituent-full))
(defun create-mm-constituent-full
    (name generics implementation-generics extends implements
     interface-imports implementation-imports created author
     description-lines fields methods impl-file-helper &rest flags)
  (create-mm-constituent-full* name
                               generics implementation-generics
                               extends implements
                               interface-imports implementation-imports
                               created author
                               description-lines
                               fields methods
                               (when impl-file-helper
                                 (format nil "~A~A" *path-to-helpers* impl-file-helper))
                               flags))

(declaim (ftype (function (string
                           (list-of (list-of string))
                           (list-of java-import)
                           (list-of java-import)
                           string string
                           (list-of (or string null))
                           &optional
                           (list-of string)
                           (list-of mm-field)
                           (list-of java-method)
                           (or null string pathname file-stream))
                          (values mm-factory &optional))
                create-mm-factory))
(defun create-mm-factory
    (name generics interface-imports implementation-imports
     created author description-lines
     &optional skip-list fields methods impl-file-helper)
  (create-mm-factory* name generics interface-imports implementation-imports
                      created author description-lines
                      skip-list fields methods
                      (when impl-file-helper
                        (format nil "~A~A" *path-to-helpers* impl-file-helper))))

(declaim (ftype (function (mm-type)
                          (values (or string null) &optional))
                mm-type-package))
(defun mm-type-package (instance)
  (with-accessors ((slot mm-type-package/tag))
      instance
    (unless (symbolp slot)
      slot)))

(declaim (ftype (function (mm-type (or string null))
                          (values (or string null) &optional))
                (setf mm-type-package)))
(defsetf mm-type-package (instance) (new-value)
  `(setf (mm-type-package/tag ,instance) ,new-value))

(declaim (ftype (function (mm-type)
                          (values (or symbol null) &optional))
                mm-type-tag))
(defun mm-type-tag (instance)
  (with-accessors ((slot mm-type-package/tag))
      instance
    (when (symbolp slot)
      slot)))

(declaim (ftype (function (mm-type symbol)
                          (values symbol &optional))
                (setf mm-type-tag)))
(defsetf mm-type-tag (instance) (new-value)
  `(setf (mm-type-package/tag ,instance) ,new-value))

(declaim (type string *model-package*))
(defvar *model-package*)

(declaim (ftype (function (mm-type)
                          (values java-type &optional))
                mm-type->java-type))
(defun mm-type->java-type (mm-type)
  (with-accessors ((package  mm-type-package)
                   (tag      mm-type-tag)
                   (name     mm-type-name)
                   (generics mm-type-generics)
                   (encl     mm-type-enclosing-type))
      mm-type
    (let ((generics (mapcar (curry #'apply #'create-java-generic) generics))
          (encl     (when encl (mm-type->java-type encl))))
      (if tag
          (create-java-type *model-package* (generate-constituent-class-name name) generics encl)
          (create-java-type package name generics encl)))))

(declaim (ftype (function (java-type)
                          (values mm-type &optional))
                java-type->mm-type))
(defun java-type->mm-type (java-type)
  (with-accessors ((package  java-type-package)
                   (name     java-type-name)
                   (generics java-type-generics)
                   (encl     java-type-enclosing-type))
      java-type
    (let ((generics (mapcar #'java-type->mm-type generics))
          (encl     (when encl (java-type->mm-type encl))))
      (create-mm-type package name generics encl))))

(deftype-list-of mm-constituent)
(declaim (ftype (function (mm-constituent (list-of mm-constituent)
                                          (function (mm-constituent) t))
                          (values mm-constituent &optional))
                flatten-implements))
(defun flatten-implements (constituent constituents predicate)
  (with-accessors ((implements mm-constituent-implements)
                   (fields mm-constituent-fields))
      constituent
    (let ((inherited-member (member-if (conjoin
                                        #'constituent-p
                                        (compose
                                         (lambda (constituent-name)
                                           (let ((constituent-member (member constituent-name constituents
                                                                             :key #'mm-constituent-name
                                                                             :test #'string=)))
                                             (not (not (and constituent-member
                                                            (funcall predicate
                                                                     (car constituent-member)))))))
                                         #'mm-type-name))
                                       implements)))
      (if (null inherited-member)
          constituent
          (progn
            (rotatef (car inherited-member) (car implements))
            (destructuring-bind (inherited-member &rest rest-of-inherited) implements
              (with-accessors ((inherited-name mm-type-name)) inherited-member
                (let ((inherited (car (member inherited-name constituents
                                              :key #'mm-constituent-name :test #'string-equal)))
                      (new-constituent (copy-mm-constituent constituent)))
                  (with-accessors ((new-implements mm-constituent-implements)
                                   (new-fields     mm-constituent-fields))
                      new-constituent
                    (setf new-implements (copy-list
                                          (append rest-of-inherited
                                                  (mm-constituent-implements inherited)))
                          new-fields     (copy-list
                                          (append (remove-if (lambda (member)
                                                               (member (mm-field-name member)
                                                                       fields
                                                                       :key #'mm-field-name
                                                                       :test #'string=))
                                                             (mm-constituent-fields inherited))
                                                  new-fields))))
                  new-constituent))))))))

(declaim (ftype (function (mm-constituent (list-of mm-constituent) (function (mm-constituent) t))
                          (values mm-constituent &optional))
                flatten-extends))
(defun flatten-extends (constituent constituents predicate)
  (with-accessors ((extends mm-constituent-extends)) constituent
    (if (or (not extends) (not (constituent-p extends)))
        constituent
        (let* ((inherited-member (member (mm-type-name extends)
                                         constituents
                                         :key #'mm-constituent-name :test #'string-equal))
               (inherited        (car inherited-member)))
          (if (or (null inherited-member) (not (funcall predicate inherited)))
              constituent
              (let ((new-constituent (copy-mm-constituent constituent)))
                (with-accessors ((new-extends mm-constituent-extends)
                                 (new-fields  mm-constituent-fields))
                    new-constituent
                  (setf new-extends (mm-constituent-extends inherited)
                        new-fields  (copy-list
                                     (append (remove-if (lambda (member)
                                                          (member (mm-field-name member)
                                                                  (mm-constituent-fields constituent)
                                                                  :key #'mm-field-name
                                                                  :test #'string=))
                                                        (mm-constituent-fields inherited))
                                             new-fields))))
                new-constituent))))))

(declaim (ftype (function ((list-of mm-constituent) &optional
                           (function (mm-constituent) t)
                           (function (mm-constituent) t))
                          (values (list-of mm-constituent) &optional))
                flatten))
(defun flatten (constituents &optional
                (implements-flatten-p (lambda (c) (declare (ignore c)) t))
                (extends-flatten-p    (lambda (c) (declare (ignore c)) nil)))
  (let ((constituents
         (loop
            with old-constituents
            and  new-constituents = constituents
            do (setq old-constituents new-constituents)
            do (setq new-constituents (mapcar (rcurry #'flatten-extends
                                                      old-constituents extends-flatten-p)
                                              old-constituents))
            until (equal old-constituents new-constituents)
            finally (return new-constituents))))
    (loop
       with old-constituents
       and  new-constituents = constituents
       do (setq old-constituents new-constituents)
       do (setq new-constituents (mapcar (rcurry #'flatten-implements
                                                 old-constituents implements-flatten-p)
                                         old-constituents))
       until (equal old-constituents new-constituents)
       finally (return new-constituents))))

(declaim (type symbol *model-symbol*))
(defvar *model-symbol*)

(declaim (type (list-of mm-constituent) *constituents*))
(defvar *constituents*)

(declaim (type mm-factory *factory*))
(defvar *factory*)

(declaim (type mm-visitor *visitor*))
(defvar *visitor*)

(declaim (type java-enum *kind-enum*))
(defvar *kind-enum*)

(eval-when (:compile-toplevel :load-toplevel :execute)
  (defparameter *meta-model-functions* (make-hash-table)))
(defmacro defMMfun (name lambda-list &body body)
  `(eval-when (:compile-toplevel :load-toplevel :execute)
     (progn
       (export ',name)
       (setf (gethash ',name *meta-model-functions*) t)
       #-sbcl (declaim (notinline ,name)) 
       (defun ,name ,lambda-list
         ,@(or body
               `((declare (ignore
                           ,@(mapcar (lambda (arg)
                                       (if (listp arg)
                                           (first arg)
                                           arg))
                                     (remove-if (lambda (elt)
                                                  (member elt lambda-list-keywords))
                                                lambda-list))))
                 (error ,(format nil "Not Implemented Function (~A)" name))))))))

(declaim (ftype (function (mm-type &optional t)
                          (values (or string null) &optional))
                extract-kind))
(defMMfun extract-kind (type &optional with-prefix)
  (declare (ignore with-prefix))
  (let ((kind (get-enum-constant type)))
    (when (and kind
               (member kind (java-enum-constants *kind-enum*)
                       :test #'string=))
      kind)))

(declaim (ftype (function (string)
                          (values string &optional))
                generate-constituent-class-name))
(defMMfun generate-constituent-class-name (name))

(declaim (ftype (function (string)
                          (values string &optional))
                generate-constituent-implementation-class-name))
(defMMfun generate-constituent-implementation-class-name (name)
  (subseq (generate-constituent-class-name name) 1))


(declaim (ftype (function (mm-type &optional t)
                          (values (or string null) &optional))
                resolve-constituent-class))
(defMMfun resolve-constituent-class (type &optional generics)
  (resolve-type-name (mm-type->java-type type) generics))

(declaim (ftype (function (mm-field &optional t)
                          (values mm-type &optional))
                get-constituent-field-class))
(defMMfun get-constituent-field-class (field &optional (bare-type-p t)))

(declaim (ftype (function (mm-field &optional t)
                          (values string &optional))
                resolve-constituent-field-class))
(defMMfun resolve-constituent-field-class (field &optional (bare-type-p t))
  (resolve-type-name (mm-type->java-type (get-constituent-field-class field bare-type-p))))

(declaim (ftype (function (mm-type)
                          (values t &optional))
                primitive-type-p))
(defun primitive-type-p (type)
  (and (null (mm-type-package type))
       (member (mm-type-name type)
               '("byte" "short" "int" "long"
                 "float" "double"
                 "character" "String")
               :test #'string=)))

(declaim (ftype (function (mm-type)
                          (values t &optional))
                constituent-p))
(defun constituent-p (type)
  (equal *model-symbol* (mm-type-tag type)))

(declaim (ftype (function (mm-type)
                          (values (or string null) &optional))
                get-enum-constant))
(defun get-enum-constant (type)
  (if (constituent-p type)
      (substitute  #\_ #\Space
                   (string-upcase (mm-type-name type))
                   :test #'char=)
      (error "Type must be a constituent type")))

(declaim (ftype (function ((list-of mm-constituent))
                          (values (list-of string) &optional))
                get-enum-constants))
(defun get-enum-constants (constituents)
  (mapcar (compose #'get-enum-constant
                   (lambda (constituent)
                     (create-mm-type *model-symbol* (mm-constituent-name constituent))))
          (flatten constituents)))

(deftype-list-of function)
(declaim (type (list-of function
                        #+(or) (function (mm-constituent)
                                         (values (or java-method null) &optional)))
               *method-generators*))
(defvar *method-generators* nil)

(declaim (ftype (function (mm-constituent)
                          (values (list-of java-method) &optional))
                generate-method-implementation))
(defun generate-method-implementation (constituent)
  (remove nil
          (mapcar (rcurry #'funcall constituent)
                  *method-generators*)))

(defmacro defgenerator (name (constituent-sym) predicate
                        &body generator-body)
  "Creates a new function and put it in *method-generators*.

constituent-sym is a variable that will be bind to the constituent
 in the generator function.

predicate should be one of the following:
  -  A string matching the name of the constituent for
     which the generator will be called.
  - A function (or something coercible to a function) getting one
    argument (the constituent) and returning a generalized boolean.
    If the function returns a non-nil result, the generator is called.
  - The body of a lambda that could have been passed instead
    (the symbol bind to the constituent is constituent-sym within this block)

    By example (defgenerator foo const (if const *bar* *baz*) *methods*)
    is roughly equivalent to (defgenerator foo const (lambda (const)
    (if const *bar* *baz*)) *methods*)

If predicate is nil the generator is called for every constituent."
  `(progn
     (declaim (ftype (function (mm-constituent)
                               (values (or java-method null) &optional))
                     ,name))
     (defun ,name (,constituent-sym)
       (when ,(match (predicate)
                     (nil 't)
                     ((:typep string)   `(string= (mm-constituent-name ,constituent-sym) ,predicate))
                     ((:typep list)     `(progn ,@predicate))
                     (_ `(funcall (coerce ,predicate 'function) ,constituent-sym)))
         ,@generator-body))
     (push #',name *method-generators*)))

(declaim (ftype (function (string mm-field)
                          (values string &optional))
                generate-list-getter-name
                generate-list-adder-name
                generate-list-remover-name))
(defMMfun generate-list-getter-name (constituent-name field)
  (declare (ignore constituent-name))
  (format nil "get~As" (generate-programmatic-name (mm-field-name field))))
(defMMfun generate-list-adder-name (constituent-name field)
  (declare (ignore constituent-name))
  (format nil "add~A" (generate-programmatic-name (mm-field-name field))))
(defMMfun generate-list-remover-name (constituent-name field)
  (declare (ignore constituent-name))
  (format nil "remove~A" (generate-programmatic-name (mm-field-name field))))

(declaim (ftype (function (string mm-field)
                          (values (list-of string) &optional))
                generate-list-getter-exceptions
                generate-list-adder-exceptions
                generate-list-remover-exceptions))
(defMMfun generate-list-getter-exceptions (constituent-name field)
  (declare (ignore constituent-name field))
  ())
(defMMfun generate-list-adder-exceptions (constituent-name field)
  (declare (ignore constituent-name field))
  ())
(defMMfun generate-list-remover-exceptions (constituent-name field)
  (declare (ignore constituent-name field))
  ())

(declaim (ftype (function (string mm-field)
                          (values string &optional))
                generate-list-remover-constituent-type))
(defMMfun generate-list-remover-constituent-type (constituent-name field)
  (declare (ignore constituent-name))
  (resolve-constituent-field-class field))

(declaim (ftype (function (string mm-field string)
                          (values string &optional))
                generate-list-remover-constituent-id-extractor))
(defMMfun generate-list-remover-constituent-id-extractor (constituent-name field variable-name)
  (declare (ignore constituent-name field))
  variable-name)

(declaim (ftype  (function (string mm-field)
                           (values (list-of java-method) &optional))
                 generate-list-accessors))
(defun generate-list-accessors (constituent-name field)
  (with-accessors ((name  mm-field-name)
                   (type  mm-field-type)
                   (flags mm-field-flags))
      field
    (let ((class-name               (resolve-constituent-field-class field))
          (member-name              (get-member name t))
          (variable-name            (generate-variable-name
                                     (format nil "~:[~;A ~]~A"
                                             (and (constituent-p type)
                                                  (string-equal name (mm-type-name type)))
                                             name)))
          (comment-name             (string-downcase name))
          (constituent-comment-name (string-downcase constituent-name)))
      (list
       (create-whole-java-method
        ('(:public) #J(nil "void") (generate-list-adder-name constituent-name field)
          `(("int" "anIndex") (,class-name ,variable-name))
          () (generate-list-adder-exceptions constituent-name field))
        `(,(format nil "~A;"
                   (generate-method-call member-name `("anIndex" ,variable-name) nil "add"
                                         '(("int" "anIndex") ("%%CONSTITUENT%%" "constituent")))))
        `(,(format nil "Adds a \"~A\" at the index (or move it there)" comment-name)))
       (create-whole-java-method
        ('(:public) #J(nil "void") (generate-list-adder-name constituent-name field)
          `((,class-name ,variable-name))
          () (generate-list-adder-exceptions constituent-name field))
        `(,(format nil "~A;"
                   (generate-method-call member-name `(,variable-name) nil
                                         "add" '(("%%CONSTITUENT%%" "constituent")))))
        `(,(format nil "Adds a \"~A\" at the end of the list (or move it there)" comment-name)))
       (create-whole-java-method
        ('(:public) #J(nil "void") (generate-list-remover-name constituent-name field)
          `((,(generate-list-remover-constituent-type constituent-name field) ,variable-name))
          () (generate-list-remover-exceptions constituent-name field))
        `(,(format nil "~A;"
                   (generate-method-call member-name `(,variable-name) nil
                                         "remove" '(("%%CONSTITUENT%%" "constituent")))))
        `(,(format nil "Removes this ~A from the list" comment-name)))
       (create-whole-java-method
        ('(:public) #J(nil "void") (generate-list-remover-name constituent-name field)
          '(("int" "anIndex"))
          () (generate-list-remover-exceptions constituent-name field))
        `(,(format nil "~A;"
                   (generate-method-call member-name `("anIndex") nil
                                         "remove" '(("int" "anIndex")))))
        `(,(format nil "Remove the ~A at the index" comment-name)))
       (create-whole-java-method
        ('(:public) #J(nil "List" `(,(create-java-generic class-name)))
          (generate-list-getter-name constituent-name field)
          () () (generate-list-getter-exceptions constituent-name field))
        `(,(format nil "return ~A;"
                   (generate-method-call "Collections" `(,member-name) nil
                                         "unmodifiableList" '(("%%LIST%%" "list")))))
        `(,(format nil "Returns the list of ~As of this ~A" comment-name constituent-comment-name)
           ,@(when (member :container flags)
                   '("<em>Part of the enclosed elements.</em>"))))))))

(declaim (ftype (function (string mm-field)
                          (values string &optional))
                generate-set-getter-name
                generate-set-adder-name
                generate-set-remover-name))
(defMMfun generate-set-getter-name (constituent-name field)
  (declare (ignore constituent-name))
  (format nil "get~As" (generate-programmatic-name (mm-field-name field))))
(defMMfun generate-set-adder-name (constituent-name field)
  (declare (ignore constituent-name))
  (format nil "add~A" (generate-programmatic-name (mm-field-name field))))
(defMMfun generate-set-remover-name (constituent-name field)
  (declare (ignore constituent-name))
  (format nil "remove~A" (generate-programmatic-name (mm-field-name field))))

(declaim (ftype (function (string mm-field)
                          (values (list-of string) &optional))
                generate-set-getter-exceptions
                generate-set-adder-exceptions
                generate-set-remover-exceptions))
(defMMfun generate-set-getter-exceptions (constituent-name field)
  (declare (ignore constituent-name field))
  ())
(defMMfun generate-set-adder-exceptions (constituent-name field)
  (declare (ignore constituent-name field))
  ())
(defMMfun generate-set-remover-exceptions (constituent-name field)
  (declare (ignore constituent-name field))
  ())

(declaim (ftype (function (string mm-field)
                          (values string &optional))
                generate-set-remover-constituent-type))
(defMMfun generate-set-remover-constituent-type (constituent-name field)
  (declare (ignore constituent-name))
  (resolve-constituent-field-class field))

(declaim (ftype (function (string mm-field string)
                          (values string &optional))
                generate-set-remover-constituent-id-extractor))
(defMMfun generate-set-remover-constituent-id-extractor (constituent-name field variable-name)
  (declare (ignore constituent-name field))
  variable-name)

(declaim (ftype  (function (string mm-field)
                           (values (list-of java-method) &optional))
                 generate-set-accessors))
(defun generate-set-accessors (constituent-name field)
  (with-accessors ((name  mm-field-name)
                   (type  mm-field-type)
                   (flags mm-field-flags))
      field
    (let* ((class-name               (resolve-constituent-field-class field))
           (member-name              (get-member name t))
           (variable-name            (generate-variable-name
                                      (format nil "~:[~;A ~]~A"
                                              (and (constituent-p type)
                                                   (string-equal name (mm-type-name type)))
                                              name)))
           (comment-name             (string-downcase name))
           (constituent-comment-name (string-downcase constituent-name)))
      (list
       (create-whole-java-method
        ('(:public) #J(nil "void") (generate-set-adder-name constituent-name field)
          `((,class-name ,variable-name))
          () (generate-set-adder-exceptions constituent-name field))
        `(,(format nil "~A;"
                   (generate-method-call member-name `(,variable-name) nil
                                         "add" '(("%%CONSTITUENT%%" "constituent")))))
        `(,(format nil "Adds a \"~A\" in the set, if it was not already in it" comment-name)))
       (create-whole-java-method
        ('(:public) #J(nil "void") (generate-set-remover-name constituent-name field)
          `((,(generate-set-remover-constituent-type constituent-name field) ,variable-name))
          () (generate-set-remover-exceptions constituent-name field))
        `(,(format nil "~A;"
                   (generate-method-call member-name `(,variable-name) nil
                                         "remove" '(("%%CONSTITUENT%%" "constituent")))))
        `(,(format nil "Removes this ~A from the set" comment-name)))
       (create-whole-java-method
        ('(:public) #J(nil "Set" `(,(create-java-generic class-name)))
          (generate-set-getter-name constituent-name field)
          ()  () (generate-set-getter-exceptions constituent-name field))
        `(,(format nil "return ~A;"
                   (generate-method-call "Collections" `(,member-name) nil
                                         "unmodifiableSet" '(("%%SET%%" "set")))))
        `(,(format nil "Returns the set of ~As of this ~A" comment-name constituent-comment-name)
           ,@(when (member :container flags)
                   '("<em>Part of the enclosed elements.</em>"))))))))

(declaim (ftype (function (string mm-field)
                          (values string &optional))
                generate-atom-getter-name
                generate-atom-setter-name))
(defMMfun generate-atom-getter-name (constituent-name field) 
  (declare (ignore constituent-name))
  (format nil "get~A" (generate-programmatic-name (mm-field-name field))))
(defMMfun generate-atom-setter-name (constituent-name field)
  (declare (ignore constituent-name))
  (format nil "set~A" (generate-programmatic-name (mm-field-name field))))

(declaim (ftype (function (string mm-field)
                          (values (list-of string) &optional))
                generate-atom-getter-exceptions
                generate-atom-setter-exceptions))
(defMMfun generate-atom-getter-exceptions (constituent-name field)
  (declare (ignore constituent-name field))
  ())
(defMMfun generate-atom-setter-exceptions (constituent-name field)
  (declare (ignore constituent-name field))
  ())

(declaim (ftype (function (string mm-field)
                          (values (list-of java-method) &optional))
                generate-atom-accessors))
(defun generate-atom-accessors (constituent-name field)
  (with-accessors ((name mm-field-name)
                   (type mm-field-type)
                   (flags mm-field-flags))
      field
    (let ((container-p              (member :container flags))
          (nullable-p               (member :nullable  flags))
          (class                    (mm-type->java-type (get-constituent-field-class field)))
          (member-name              (get-member name))
          (variable-name            (generate-variable-name name))
          (comment-name             (string-downcase name))
          (constituent-comment-name (string-downcase constituent-name)))
      `(
        ,@(unless (member :final flags)
                  `(,(create-whole-java-method
                      ('(:public) #J(nil "void") (generate-atom-setter-name constituent-name field)
                        `((,(resolve-type-name class) ,variable-name))
                        () (generate-atom-setter-exceptions constituent-name field))
                      `(,(if (not container-p)
                             (format nil "~A = ~A;" member-name variable-name)
                             (format nil "~A;"
                                     (generate-method-call member-name `(,variable-name) nil
                                                         "set" '(("%%CONSTITUENT%%" "constituent"))))))
                      `(,(format nil "Modifies the ~A of this ~A" comment-name constituent-comment-name))
                      `(,(format nil "~A the new ~A~@[, can be {@code null}~]"
                                 variable-name comment-name nullable-p)))))
        ,(create-whole-java-method
          ('(:public) class (generate-atom-getter-name constituent-name field)
            ()  () (generate-atom-getter-exceptions constituent-name field))
          `(,(format nil "return ~A;"
                     (if container-p
                         (generate-method-call member-name () nil "get")
                         member-name)))
          `(,(format nil "Returns the ~A of this ~A" comment-name constituent-comment-name)
             ,@(when (member :container flags)
                     '("<em>Included in the enclosed elements.</em>")))
          ()
          (when nullable-p (format nil "null iff there is no ~A" comment-name)))))))

(declaim (ftype (function (mm-field)
                          (values (or string null) &optional))
                generate-atom-field-initialization))
(defMMfun generate-atom-field-initialization (field))

(declaim (ftype (function (string mm-field)
                          (values java-field &optional))
                generate-constituent-atom-field))
(defun generate-constituent-atom-field (constituent-name field)
  (with-accessors ((name mm-field-name)
                   (type mm-field-type)
                   (flags mm-field-flags))
      field
    (let ((container-p              (member :container flags))
          (final-p                  (member :final     flags))
          (comment-name             (string-downcase name))
          (constituent-comment-name (string-downcase constituent-name)))
      (create-java-field
       `(,(if (member :protected flags) :protected :private)
          ,@(when (or container-p final-p) '(:final))
          ,@(when (member :transient flags) '(:transient)))
       (mm-type->java-type (get-constituent-field-class field nil))
       name
       `(,(format nil "~A of this ~A" comment-name constituent-comment-name))
       (generate-atom-field-initialization field)))))

(declaim (ftype  (function (mm-field)
                           (values (or string null) &optional))
                 generate-list-field-initialization))
(defMMfun generate-list-field-initialization (field))

(declaim (ftype  (function (string mm-field)
                           (values java-field &optional))
                 generate-constituent-list-field))
(defun generate-constituent-list-field (constituent-name field)
  (with-accessors ((name mm-field-name)
                   (type mm-field-type)
                   (flags mm-field-flags))
      field
    (let* ((name                     (format nil "~As" name))
           (comment-name             (string-downcase name))
           (constituent-comment-name (string-downcase constituent-name))
           (class                    (resolve-constituent-field-class field)))
      (create-java-field
       '(:private :final) #J(nil "List" `(,(create-java-generic class))) name
       `(,(format nil "List of ~A of this ~A" comment-name constituent-comment-name))
       (generate-list-field-initialization field)))))

(declaim (ftype  (function (mm-field)
                           (values (or string null) &optional))
                 generate-set-field-initialization))
(defMMfun generate-set-field-initialization (field))

(declaim (ftype  (function (string mm-field)
                           (values java-field &optional))
                 generate-constituent-set-field))
(defun generate-constituent-set-field (constituent-name field)
  (with-accessors ((name mm-field-name)
                   (type mm-field-type)
                   (flags mm-field-flags))
      field
    (let* ((name                     (format nil "~As" name))
           (comment-name             (string-downcase name))
           (constituent-comment-name (string-downcase constituent-name))
           (class                    (resolve-constituent-field-class field)))
      (create-java-field
       '(:private :final) #J(nil "Set" `(,(create-java-generic class))) name
       `(,(format nil "Set of ~A of this ~A" comment-name constituent-comment-name))
       (generate-set-field-initialization field)))))

(declaim (ftype (function (mm-constituent)
                          (values (list-of java-import) &optional))
                generate-constituent-interface-imports))
(defMMfun generate-constituent-interface-imports (constituent))

(declaim (ftype  (function (mm-constituent)
                           (values (list-of java-import) &optional))
                 generate-constituent-implementation-imports))
(defMMfun generate-constituent-implementation-imports (constituent))

(declaim (ftype (function (java-enum (list-of mm-constituent))
                          (values java-enum &optional))
                generate-actual-enum-kind))
(defun generate-actual-enum-kind (original-kind-enum constituents)
  (let ((kind-enum (copy-java-enum original-kind-enum)))
    (setf (java-enum-constants kind-enum)
          (append (java-enum-constants kind-enum)
                  (generate-enum-kind-constants constituents)))
    kind-enum))

(declaim (ftype (function ((list-of mm-constituent))
                          (values (list-of string) &optional))
                generate-enum-kind-constants))
(defMMfun generate-enum-kind-constants (constituents))

(declaim (ftype (function (mm-constituent)
                          (values java-class &optional))
                generate-constituent-interface))
(defun generate-constituent-interface (constituent)
  (with-accessors ((name mm-constituent-name)
                   (generics mm-constituent-generics)
                   (implements mm-constituent-implements)
                   (imports mm-constituent-interface-imports)
                   (created mm-constituent-created)
                   (author mm-constituent-author)
                   (description-lines mm-constituent-description-lines)
                   (components mm-constituent-fields)
                   (methods mm-constituent-methods)
                   (flags mm-constituent-flags))
      constituent
    (create-java-class
     '(:public :interface)
     (generate-constituent-class-name name)
     (mapcar (curry #'apply #'create-java-generic) generics)
     nil
     (mapcar #'mm-type->java-type implements)
     (append
      (generate-constituent-interface-imports constituent)
      imports)
     created
     author
     description-lines
     ()
     (remove-if
      (compose (disjoin
                (compose #'not (curry #'member :public)
                         #'java-method-modifiers)
                (compose (curry #'member :impl-only)
                         #'java-method-flags))
               #'java-method-signature)
      (append methods
              (generate-method-implementation constituent)
              (mappend (lambda (component)
                         (with-accessors ((flags mm-field-flags)) component
                           (cond
                             ((member :list flags) (generate-list-accessors name component))
                             ((member :set  flags) (generate-set-accessors  name component))
                             (t                    (generate-atom-accessors name component)))))
                       (remove-if (compose
                                   (disjoin (curry #'member :no-accessor)
                                            (curry #'member :implementation-only-accessor))
                                   #'mm-field-flags)
                                  components)))))))

(declaim (ftype (function (string mm-constituent)
                          (values java-class &optional))
                generate-constituent-implementation))
(defun generate-constituent-implementation (ipackage constituent)
  (with-accessors ((name mm-constituent-name)
                   (generics mm-constituent-implementation-generics)
                   (extends mm-constituent-extends)
                   (implements mm-constituent-implements)
                   (imports mm-constituent-implementation-imports)
                   (created mm-constituent-created)
                   (author mm-constituent-author)
                   (description-lines mm-constituent-description-lines)
                   (components mm-constituent-fields)
                   (methods mm-constituent-methods)
                   (flags mm-constituent-flags)
                   (impl-file-helper mm-constituent-impl-file-helper))
      constituent
    (let ((interface-name (generate-constituent-class-name name))
          (impl-only-p    (member :impl-only flags)))
      (create-java-class
       (append
        (when (member :public flags) '(:public))
        (when (member :abstract flags) '(:abstract))
        '(:class))
       (generate-constituent-implementation-class-name name)
       (mapcar (curry #'apply #'create-java-generic) generics)
       (when extends
         (let* ((class (mm-type->java-type extends)))
           (create-java-type (java-type-package class)
                             (if (constituent-p extends)
                                 (subseq (java-type-name class) 1)
                                 (java-type-name class))
                             (java-type-generics class)
                             (java-type-enclosing-type class))))
       (if impl-only-p
           (mapcar #'mm-type->java-type implements)
           `(,(create-java-type ipackage interface-name
                                (mapcar (compose #'create-java-generic #'first)
                                        (mm-constituent-generics constituent)))))
       (append
        (generate-constituent-implementation-imports constituent)
        imports
        (unless impl-only-p
          `(nil ,(format nil "~A.~A" ipackage interface-name))))
       created author
       (append description-lines
               (unless impl-only-p `("" ,(format nil "Default implementation for {@link ~A.~A}"
                                                 ipackage interface-name))))
       (mapcar (lambda (component)
                 (with-accessors ((flags mm-field-flags)) component
                   (cond
                     ((member :list flags) (generate-constituent-list-field name component))
                     ((member :set  flags) (generate-constituent-set-field  name component))
                     (t                    (generate-constituent-atom-field name component)))))
               (remove-if (compose
                           (curry #'member :intrf-only)
                           #'mm-field-flags)
                          components))
       (remove-if
        (conjoin
         (compose (curry #'eq :abstract) #'java-method-body)
         #+(or) (lambda (_) (declare (ignore _)) t)
         #+(and) (compose (curry #'member :public) #'java-method-modifiers #'java-method-signature))
        (append methods
                (mappend (lambda (component)
                           (with-accessors ((flags mm-field-flags)) component
                             (cond
                               ((member :list flags) (generate-list-accessors name component))
                               ((member :set  flags) (generate-set-accessors  name component))
                               (t                    (generate-atom-accessors name component)))))
                         (remove-if (compose
                                     (disjoin (curry #'member :no-accessor)
                                              (curry #'member :interface-only-accessor)
                                              (curry #'member :intrf-only))
                                     #'mm-field-flags)
                                    components))
                (generate-method-implementation constituent)))
       impl-file-helper))))

(declaim (ftype (function ((list-of (list-of string)))
                          (values (list-of (list-of string)) &optional))
                generate-create-arguments-from-constructor))
(defMMfun generate-create-arguments-from-constructor (constructor-arguments))

(declaim (ftype  (function ((list-of (list-of string)))
                           (values (list-of string) &optional))
                 generate-in-create-constructor-agruments))
(defMMfun generate-in-create-constructor-agruments (constructor-arguments))

(declaim (ftype (function (string string (list-of string))
                          (values (list-of string) &optional))
                generate-factory-create-method-body))
(defMMfun generate-factory-create-method-body (constituent-name class-name constructor-arguments)
  (declare (ignore constituent-name))
  `(,(format nil "return new ~A(~{~A~^, ~});" class-name constructor-arguments)))

(declaim (ftype (function (mm-factory (list-of mm-constituent))
                          (values java-class &optional))
                generate-factory-interface))
(defun generate-factory-interface (factory constituents)
  (with-accessors ((name mm-factory-name)
                   (generics mm-factory-generics)
                   (imports mm-factory-interface-imports)
                   (created mm-factory-created)
                   (author mm-factory-author)
                   (description-lines mm-factory-description-lines)
                   (skip-list mm-factory-skip-list)
                   (fields mm-factory-fields)
                   (methods mm-factory-methods))
      factory
    (create-java-class
     '(:public :interface)
     (generate-constituent-class-name name)
     generics () ()
     imports
     created author
     description-lines
     ()
     (remove-if
      (compose (disjoin
                (compose (disjoin
                          (curry #'member :static)
                          (compose #'not (curry #'member :public)))
                         #'java-method-modifiers)
                (compose (curry #'member :impl-only)
                         #'java-method-flags))
               #'java-method-signature)
      (append
       methods
       (mappend (lambda (component)
                  (with-accessors ((flags mm-field-flags)) component
                    (cond
                      ((member :list flags) (generate-list-accessors name component))
                      ((member :set  flags) (generate-set-accessors  name component))
                      (t                    (generate-atom-accessors name component)))))
                (remove-if (compose
                            (disjoin (curry #'member :no-accessor)
                                     (curry #'member :implementation-only-accessor))
                            #'mm-field-flags)
                           fields))
       (mapcar (lambda (constituent)
                 (with-accessors ((constituent-name mm-constituent-name)
                                  (methods mm-constituent-methods)
                                  (generics mm-constituent-generics))
                     constituent
                   (let* ((comment-name          (string-downcase constituent-name))
                          (interface-name        (generate-constituent-class-name constituent-name))
                          (implementation-name   (generate-constituent-implementation-class-name constituent-name))
                          (constructor           (car (member implementation-name methods
                                                              :key (compose #'java-method-name
                                                                            #'java-method-signature)
                                                              :test #'string=)))
                          (create-name           (generate-variable-name (format nil "Create ~A" constituent-name)))
                          (constructor-arguments (java-method-arguments (java-method-signature constructor)))
                          (create-arguments      (generate-create-arguments-from-constructor constructor-arguments))
                          (generics              (mapcar (curry #'apply #'create-java-generic)
                                                         generics)))
                     (create-whole-java-method
                      ('(:public) #J(nil interface-name (mapcar (compose #'create-java-generic #'java-generic-name) generics))
                        create-name create-arguments generics)
                      :abstract
                      `(,(format nil "Returns a new ~A." comment-name))))))
               (remove-if (compose (rcurry #'member skip-list :test #'string=) #'mm-constituent-name)
                          constituents)))))))

(declaim (ftype (function (string)
                          (values string &optional))
                %generate-constituent-create-method-name))
(defMMfun %generate-constituent-create-method-name (constituent-name)
  (generate-variable-name (format nil "Create ~A" constituent-name)))

(declaim (ftype (function (string)
                          (values (list-of string) &optional))
                %generate-constituent-create-method-exceptions))
(defMMfun %generate-constituent-create-method-exceptions (constituent-name)
  (declare (ignore constituent-name))
  ())

(declaim (ftype (function (string (list-of (list-of string)) (or java-method null))
                          (values (or java-method null) &optional))
                %generate-constituent-create-method))
(defMMfun %generate-constituent-create-method (name generics constructor)
  (when constructor
    (let* ((comment-name          (string-downcase name))
           (interface-name        (generate-constituent-class-name name))
           (implementation-name   (generate-constituent-implementation-class-name name))
           (create-name           (%generate-constituent-create-method-name name))
           (constructor-arguments (java-method-arguments (java-method-signature constructor)))
           (create-arguments      (generate-create-arguments-from-constructor constructor-arguments))
           (constructor-arguments (generate-in-create-constructor-agruments constructor-arguments))
           (generics              (mapcar (curry #'apply #'create-java-generic)
                                          generics))
           (exceptions            (%generate-constituent-create-method-exceptions name)))
      (create-whole-java-method
       ('(:public) #J(nil interface-name (mapcar (compose #'create-java-generic #'java-generic-name) generics))
         create-name create-arguments generics exceptions)
       (generate-factory-create-method-body name implementation-name constructor-arguments)
       `(,(format nil "Returns a new ~A." comment-name))))))

(declaim (ftype (function (mm-constituent)
                          (values java-method &optional))
                generate-create-method))
(defun generate-create-method (constituent)
  (with-accessors ((constituent-name mm-constituent-name)
                   (methods mm-constituent-methods)
                   (generics mm-constituent-generics))
      constituent
    (let* ((comment-name          (string-downcase constituent-name))
           (interface-name        (generate-constituent-class-name constituent-name))
           (implementation-name   (generate-constituent-implementation-class-name constituent-name))
           (constructor           (mm-constituent-constructor constituent))
           (create-name           (%generate-constituent-create-method-name constituent-name))
           (constructor-arguments (java-method-arguments (java-method-signature constructor)))
           (create-arguments      (generate-create-arguments-from-constructor constructor-arguments))
           (constructor-arguments (generate-in-create-constructor-agruments constructor-arguments))
           (generics              (mapcar (curry #'apply #'create-java-generic)
                                          generics)))
      (create-whole-java-method
       ('(:public) #J(nil interface-name (mapcar (compose #'create-java-generic #'java-generic-name) generics))
         create-name create-arguments generics)
       (generate-factory-create-method-body constituent-name implementation-name constructor-arguments)
       `(,(format nil "Returns a new ~A." comment-name))))))

(declaim (ftype (function (string mm-factory (list-of mm-constituent))
                          (values java-class &optional))
                generate-factory-implementation))
(defun generate-factory-implementation (ipackage factory constituents)
  (with-accessors ((name mm-factory-name)
                   (generics mm-factory-generics)
                   (imports mm-factory-implementation-imports)
                   (created mm-factory-created)
                   (author mm-factory-author)
                   (description-lines mm-factory-description-lines)
                   (skip-list mm-factory-skip-list)
                   (fields mm-factory-fields)
                   (methods mm-factory-methods))
      factory
    (let ((interface-name (generate-constituent-class-name name)))
      (create-java-class
       '(:public :class)
       (generate-constituent-implementation-class-name name)
       generics ()
       `(,(create-java-type nil interface-name
                            (mapcar (compose #'create-java-generic #'first)
                                    generics)))
       (append
        imports
        `(nil ,(format nil "~A.~A" ipackage interface-name))
        (mapcar (compose (curry #'format nil "~A.~A" ipackage)
                         #'generate-constituent-class-name
                         #'mm-constituent-name)
                (remove-if (compose (rcurry #'member skip-list :test #'string=) #'mm-constituent-name)
                           constituents)))
       created author
       description-lines
       (mapcar (lambda (component)
                 (with-accessors ((flags mm-field-flags)) component
                   (cond
                     ((member :list flags) (generate-constituent-list-field name component))
                     ((member :set  flags) (generate-constituent-set-field  name component))
                     (t                    (generate-constituent-atom-field name component)))))
               (remove-if (compose
                           (curry #'member :intrf-only)
                           #'mm-field-flags)
                          fields))
       (remove-if
        (disjoin
         (compose (curry #'member :intrf-only) #'java-method-flags #'java-method-signature)
         (compose (curry #'eq :abstract) #'java-method-body))
        (append
         methods
         (mappend (lambda (component)
                    (with-accessors ((flags mm-field-flags)) component
                      (cond
                        ((member :list flags) (generate-list-accessors name component))
                        ((member :set  flags) (generate-set-accessors  name component))
                        (t                    (generate-atom-accessors name component)))))
                  (remove-if (compose
                              (disjoin (curry #'member :no-accessor)
                                       (curry #'member :interface-only-accessor)
                                       (curry #'member :intrf-only))
                              #'mm-field-flags)
                             fields))
         (mapcar (lambda (constituent)
                   (with-accessors ((constituent-name mm-constituent-name)
                                    (methods mm-constituent-methods)
                                    (generics mm-constituent-generics))
                       constituent
                     (let* ((comment-name          (string-downcase constituent-name))
                            (interface-name        (generate-constituent-class-name constituent-name))
                            (implementation-name   (generate-constituent-implementation-class-name constituent-name))
                            (constructor           (car (member implementation-name methods
                                                                :key (compose #'java-method-name
                                                                              #'java-method-signature)
                                                                :test #'string=)))
                            (create-name           (generate-variable-name (format nil "Create ~A" constituent-name)))
                            (constructor-arguments (java-method-arguments (java-method-signature constructor)))
                            (create-arguments      (generate-create-arguments-from-constructor constructor-arguments))
                            (constructor-arguments (generate-in-create-constructor-agruments constructor-arguments))
                            (generics              (mapcar (curry #'apply #'create-java-generic)
                                                           generics)))
                       (create-whole-java-method
                        ('(:public) #J(nil interface-name (mapcar (compose #'create-java-generic #'java-generic-name) generics))
                          create-name create-arguments generics)
                        (generate-factory-create-method-body constituent-name implementation-name constructor-arguments)
                        `(,(format nil "Returns a new ~A." comment-name))))))
                 (remove-if (compose (rcurry #'member skip-list :test #'string=) #'mm-constituent-name)
                            constituents))))
       (mm-factory-impl-file-helper factory)))))

(declaim (ftype (function (mm-visitor (list-of mm-constituent))
                          (values java-class &optional))
                generate-visitor-interface))
(defun generate-visitor-interface (visitor constituents)
  (with-accessors ((name mm-visitor-name)
                   (generics mm-visitor-generics)
                   (imports mm-visitor-imports)
                   (created mm-visitor-created)
                   (author mm-visitor-author)
                   (description-lines mm-visitor-description-lines)
                   (skip-list mm-visitor-skip-list)
                   (methods mm-visitor-methods))
      visitor
    (let ((generics (mapcar (curry #'apply #'create-java-generic)
                            (append '(("R") ("P")) generics))))
      (create-java-class
       '(:public :interface)
       (generate-constituent-class-name name)
       generics () ()
       imports
       created author
       description-lines
       ()
       (remove-if
        (compose (disjoin
                  (compose (disjoin
                            (curry #'member :static)
                            (compose #'not (curry #'member :public)))
                           #'java-method-modifiers)
                  (compose (curry #'member :impl-only)
                           #'java-method-flags))
                 #'java-method-signature)
        (append
         methods
         (mapcar (lambda (constituent)
                   (with-accessors ((constituent-name mm-constituent-name)
                                    (generics mm-constituent-generics))
                       constituent
                     (let* ((comment-name          (string-downcase constituent-name))
                            (interface-name        (generate-constituent-class-name constituent-name))
                            (variable-name         (generate-variable-name (format nil "~A Element" constituent-name)))
                            (visit-name            (generate-variable-name (format nil "Visit ~A" constituent-name))))
                       (create-whole-java-method
                        ('(:public) #J(nil "R") visit-name `((,(format nil "~A~A"
                                                                       interface-name
                                                                       (generate-generics-instantiation generics))
                                                               ,variable-name)
                                                             ("P" "additionalParameter"))
                          (mapcar (curry #'apply #'create-java-generic) generics))
                        :abstract
                        `(,(format nil "Visits a ~A." comment-name))))))
                 (remove-if (compose (rcurry #'member skip-list :test #'string=) #'mm-constituent-name)
                            constituents))))))))
