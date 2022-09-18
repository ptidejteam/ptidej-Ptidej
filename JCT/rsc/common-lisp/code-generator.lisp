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

;; TODO :
;; generation of transformations from constituents :: delete remaining

(cl:defpackage #:code-generation
  (:use #:cl #:split-sequence
        #:restricted-list #:xt-pattern #:license-generation)
  (:import-from #:alexandria
                #:circular-list #:curry #:rcurry #:lastcar #:mappend #:compose #:lastcar)
  (:export #:string-replace #:get-file-lines #:generation-date #:intersperse #:uniq 

           #:resolve-type-name #:generate-type-FQN #:generate-type-import

           #:wrap-with-exception-checks

           #:generate-programmatic-name #:generate-variable-name #:generate-member-name
           #:generate-method-name
           #:get-member

           #:write-class-code

           #:generate-imports #:generate-description #:generate-extends
           #:generate-generics #:generate-generics-instantiation #:generate-generics-parameters
           #:generate-modifier-string

           #:generate-field-declaration
           #:generate-method-signature #:generate-method-call
           #:generate-method #:generate-method-with-body
           #:generate-fields
           #:generate-constants

           #:generate-factory-methods #:generate-visitor-enums-methods #:generate-visitor-methods

           #:generate-visitor-switch-method #:get-interface-method

           #:generate-interfaces #:generate-interface
           #:generate-classes #:generate-class
           #:generate-enums #:generate-enum

           #:generate-summary #:generate-summary-imports

           #:*debug-p* #:*enums* #:*interfaces* #:*visitor* #:*factory*

           #:java-import #:java-comment

           #:java-type #:create-java-type #:copy-java-type #:java-type-p
           #:java-type-package #:java-type-name #:java-type-generics #:java-type-enclosing-type

           #:java-generic #:create-java-generic #:copy-java-generic #:java-generic-p
           #:java-generic-name #:java-generic-bound

           #:java-class #:create-java-class #:copy-java-class #:java-class-p
           #:java-class-modifiers #:java-class-name #:java-class-generics
           #:java-class-extends #:java-class-implements #:java-class-imports
           #:java-class-author #:java-class-description-lines #:java-class-fields
           #:java-class-methods #:java-class-impl-file-helper

           #:java-field #:create-java-field #:copy-java-field #:java-field-p
           #:java-field-modifiers #:java-field-type #:java-field-name
           #:java-field-description-lines #:java-field-initialization

           #:java-method-signature #:create-java-method-signature #:copy-java-method-signature
           #:java-method-signature-p
           #:create-simple-java-method-signature
           #:java-method-modifiers #:java-method-return-type #:java-method-name
           #:java-method-arguments #:java-method-generics #:java-method-throws #:java-method-flags

           #:java-method #:create-java-method #:create-whole-java-method #:copy-java-method
           #:java-method-p
           #:java-method-body #:java-method-description-lines #:java-method-argument-comments
           #:java-method-return-comment #:java-method-throws-comments

           #:java-enum #:create-java-enum #:copy-java-enum #:java-enum-p
           #:java-enum-name #:java-enum-created #:java-enum-author
           #:java-enum-description-lines #:java-enum-constants))

(cl:in-package #:code-generation)


(defvar *debug-p* nil)           ; avoid warning about *debug-p* does not exists

(defvar *enums* nil)
(defvar *interfaces* nil)
(defvar *visitor* nil)
(defvar *factory* nil)


;; define structures

(eval-when (:compile-toplevel :load-toplevel :execute)
  (deftype java-import () `(or string null))
  (deftype java-comment () `(or string null))

  (defstruct (java-generic
               (:constructor create-java-generic
                             (name &optional bound)))
    (name nil :type string)
    (bound nil :type (or string null)))

  (deftype-list-of java-generic)
  (defstruct (java-type
               (:constructor create-java-type
                             (package name &optional generics enclosing-type)))
    (package nil :type (or null string))
    (name nil :type string)
    (generics () :type (list-of java-generic))
    (enclosing-type () :type (or java-type null)))

  (set-dispatch-macro-character #\# #\J
                                (lambda (stream subchar arg)
                                  (declare (ignore subchar arg))
                                  `(create-java-type ,@(read stream t nil t))))

  (deftype-list-of keyword)
  (deftype-list-of java-import)
  (deftype-list-of java-type)
  (deftype-list-of java-comment)
  (deftype-list-of java-method)
  (deftype-list-of java-field)
  (defstruct (java-class
               (:constructor create-java-class
                             (modifiers name generics extends implements imports created
                                        author description-lines fields methods
                                        &optional impl-file-helper)))
    (modifiers () :type (list-of keyword))
    (name "" :type string)
    (generics () :type (list-of java-generic))
    (extends nil :type (or java-type null))
    (implements () :type (list-of java-type))
    (imports () :type (list-of java-import))
    (created (generation-date) :type string)
    (author "" :type string)
    (description-lines () :type (list-of java-comment))
    (fields () :type (list-of java-field))
    (methods () :type (list-of java-method))
    (impl-file-helper nil :type (or null string pathname file-stream)))

  (defstruct (java-field
               (:constructor create-java-field
                             (modifiers type name
                                        &optional description-lines initialization)))
    (modifiers () :type (list-of keyword))
    (type nil :type java-type)
    (name "" :type string)
    (description-lines () :type (list-of java-comment))
    (initialization nil :type (or string null)))

  (deftype-list-of string)
  (deftype-list-of (list-of string))
  (defstruct (java-method-signature
               (:conc-name java-method-)
               (:constructor create-java-method-signature
                             (modifiers return-type name
                                        &optional arguments generics throws
                                        &rest flags))
               (:constructor create-simple-java-method-signature
                             (name &optional arguments)))
    (modifiers () :type (list-of keyword))
    (return-type nil :type (or java-type null))
    (name "" :type string)
    (arguments () :type (list-of (list-of string)))
    (generics () :type (list-of java-generic))
    (throws () :type (list-of string))
    (flags () :type (list-of keyword)))

  (defstruct (java-method
               (:constructor create-java-method
                             (signature body description-lines
                                        &optional argument-comments return-comment throws-comments)))
    (signature nil :type java-method-signature)
    (body :abstract :type (or list keyword))
    (description-lines () :type (list-of java-comment))
    (argument-comments () :type (list-of string))
    (return-comment nil :type java-comment)
    (throws-comments () :type (list-of string)))

  (defstruct (java-enum
               (:constructor create-java-enum
                             (name created author description-lines
                                   &rest constants)))
    (name "" :type string)
    (created (generation-date) :type string)
    (author "" :type string)
    (description-lines () :type (list-of java-comment))
    (constants () :type (list-of string))))

(defmacro create-whole-java-method (&whole args
                                    (&whole signature-args
                                            modifiers return-type name
                                            &optional arguments generics throws
                                            &rest flags)
                                    body description-lines
                                    &optional argument-comments return-comment throws-comments)
  (declare (ignore modifiers return-type name arguments generics throws flags
                   body description-lines argument-comments return-comment
                   throws-comments))
  `(create-java-method
    (create-java-method-signature ,@signature-args)
    ,@(cddr args)))

;; Utility Macro & Functions

(declaim (ftype (function (string string string)
                          (values string &optional))
                string-replace))
(defun string-replace (in-string old new)
  "replaces a substring within a string"
  (let ((result ""))
    (do ((begin 0)
         (end (search old in-string)
              (search old in-string :start2 begin)))
        ((>= begin (length in-string))
         'done)
      (cond
        (end
         (setf result (format nil "~A~A~A"
                              result
                              (subseq in-string begin end)
                              new))
         (setf begin  (+ end (length old))))
        (t
         (setf result (format nil "~A~A"
                              result
                              (subseq in-string begin
                                      (length in-string))))
         (setf begin  (length in-string)))))
    result))

(declaim (ftype (function ((or string pathname file-stream))
                          (values (list-of string) &optional))
                get-file-lines))
(defun get-file-lines (file-name)
  (with-open-file (file file-name)
    (loop
       for line = (read-line file nil file)
       until (eq line file)
       when line collect line)))

(declaim (ftype (function (java-type &optional t)
                          (values string &optional))
                generate-type-FQN))
(defun generate-type-FQN (type &optional generics)
  "Returns the FQN of the class denominated by this type"
  (with-accessors ((package java-type-package)) type
    (format nil "~@[~A.~]~A" package (resolve-type-name type generics))))

(declaim (ftype (function (java-type &optional (or string null))
                          (values java-import &optional))
                generate-type-import))
(defun generate-type-import (type &optional package)
  "Generates the import of the class denominated by this type.
Returns nil iif the package of the type is null or is the same as package"
  (unless (or
           (null (java-type-package type))
           (and package (string= package (java-type-package type))))
    (generate-type-FQN type nil)))

(declaim (ftype (function (java-type &optional t)
                          (values string &optional))
                resolve-type-name))
(defun resolve-type-name (type &optional generics)
  "Returns the name (FQN without package) of the class denominated by this type"
  (with-accessors ((name    java-type-name)
                   (encl    java-type-enclosing-type)
                   (gens    java-type-generics))
      type
    (let ((simple-name (format nil "~A~@[~A~]"
                               name
                               (when generics (generate-generics gens)))))
      (format nil "~@[~A.~]~A"
              (when encl (resolve-type-name encl))
              simple-name))))

(declaim (ftype (function (string)
                          (values string &optional))
                generate-programmatic-name))
(defun generate-programmatic-name (name)
  (remove #\Space
          (string-capitalize name)
          :test #'char=))

(declaim (ftype (function ((or string (list-of string)) (list-of string)
                           (function (string) (values (list-of string))))
                          (values (list-of string) &optional))
                wrap-with-exception-checks))
(defun wrap-with-exception-checks (code exceptions check-generator)
  (let ((code (if (listp code) code `(,code))))
    (if (null exceptions)
        code
        `("try"
          "{"
          ,@code
          "}"
          ,@(mappend check-generator exceptions)))))

(declaim (ftype (function (string)
                          (values string &optional))
                generate-variable-name))
(defun generate-variable-name (name)
  (let ((programatic-name (generate-programmatic-name name)))
    (setf (aref programatic-name 0)
          (char-downcase (aref programatic-name 0)))
    programatic-name))

(declaim (ftype (function (string &optional t)
                          (values string &optional))
                generate-member-name))
(defun generate-member-name (name &optional list/set-p)
  (format nil "~A~:[~;s~]" (generate-variable-name name) list/set-p))

(declaim (ftype (function (string &optional t)
                          (values string &optional))
                get-member))
(defun get-member (name &optional list/set-p)
  (format nil "this.~A" (generate-member-name name list/set-p)))

(declaim (ftype (function ()
                          (values string &optional))
                generation-date))
(defun generation-date ()
  "generates a string representing the current date, using the format \"YYYY-mm-dd (%JDOW%)\"
   %JDOW% being the japanese character for the day of the week"
  (let ((jap-dow #("日" "月" "火" "水" "木" "金" "土")))
    (multiple-value-bind (s m h day month year dow) (get-decoded-time)
      (declare (ignore s m h))
      (format nil "~4,'0D-~2,'0D-~2,'0D (~A)" year month day (aref jap-dow dow)))))

(declaim (ftype (function (string list &optional (function (* string) (values list &optional)))
                          (values list &optional))
                intersperse))
(defun intersperse (glue sequence &optional (func #'list))
  "puts glue at the beginning and between each eement of sequence"
  (mappend (lambda (item) (funcall func glue item)) sequence))

(declaim (ftype (function (list &key (:test (function (* *) t))
                                (:start unsigned-byte)
                                (:end (or unsigned-byte null))
                                (:key (or function null)))
                          (values list &optional))
                uniq))
(defun uniq (sequence &key (test #'eql) (start 0) end key)
  (let ((firstp t)
        prev)
    (remove-if (lambda (x)
                 (and (not (shiftf firstp nil))
                      (funcall test prev (setf prev x))))
               sequence
               :start start :end end :key key)))

;; write function (model)

(declaim (ftype (function (stream
                           string string string string
                           (list-of java-import)
                           (list-of string)
                           string string string string
                           (list-of string))
                          (values &optional))
                write-class-code))
(defun write-class-code (output
                         license-text author created package imports description-lines
                         decl name generics extends body-lines)
  (format output
          "/**
 * @author ~A
 * @created ~A
~@[ *
~{ * ~A~^~%~}~]
 */

package ~A;

~@[~{~@[import ~A;~]~%~}

~]/**
~{ * ~A~^~%~}
 *
 * @author ~A
 */
~A ~A~A ~A
{
~{    ~A~%~}
}
"
          author created (split-sequence #\Linefeed license-text)
          package (uniq imports :test #'string=) description-lines author
          decl name generics extends body-lines)
  (values))

;; Generating utility functions
(declaim (ftype (function ((list-of java-import))
                          (values (list-of java-import) &optional))
                generate-imports))
(defun generate-imports (imports)
  "generates import lines"
  (mapcar
   (lambda (import)
     (when import (format nil "import ~A;" import)))
   imports))

(deftype-list-of (or string null))
(declaim (ftype (function ((list-of (or string null)) &optional (list-of string))
                          (values (list-of (or string null)) &optional))
                generate-description))
(defun generate-description (description &optional implementing)
  "generates description"
  (if implementing
      `(,(format nil "Default implementation for {@link ~A.~A}"
                 (car implementing)
                 (cdr implementing)))
      description))

(declaim (ftype (function ((list-of java-generic))
                          (values string &optional))
                generate-generics))
(defun generate-generics (generics)
  "generates generics declaration"
  (format nil "~@[<~{~{~A~^ ~}~^, ~}>~]"
          (mapcar (lambda (g)
                    (if (java-generic-bound g)
                        (list (java-generic-name g)
                              (java-generic-bound g))
                        (list (java-generic-name g))))
                  generics)))

(declaim (ftype (function ((list-of (list-of string)))
                          (values string &optional))
                generate-generics-instantiation))
(defun generate-generics-instantiation (generics)
  "generates a type generics parameters"
  (generate-generics-parameters (mapcar #'first generics)))

(declaim (ftype (function ((list-of string))
                          (values string &optional))
                generate-generics-parameters))
(defun generate-generics-parameters (generics)
  "generates the generics parameter"
  (format nil "~@[<~{~A~^, ~}>~]" generics))

(declaim (ftype (function ((or java-type null) &optional (list-of java-type) t)
                          (values string &optional))
                generate-extends))
(defun generate-extends (extends &optional implements interface-p)
  "generate extends declaration"
  (assert (not (and extends interface-p)))
  (let ((extends    (if interface-p
                        (when implements (format nil "extends ~{~A~^, ~}"
                                                 (mapcar (rcurry #'resolve-type-name t) implements)))
                        (when extends    (format nil "extends ~A"
                                                 (resolve-type-name extends t)))))
        (implements (when (and (not interface-p) implements)
                      (format nil "implements ~{~A~^, ~}"
                              (mapcar (rcurry #'resolve-type-name t) implements)))))
    (format nil "~A~:[~; ~]~A"
            (or extends "")
            (and extends implements)
            (or implements ""))))

(declaim (ftype (function (string)
                          (values string &optional))
                generate-method-name))
(defun generate-method-name (constant-name)
  "generates the method-name version of the enum constant name"
  (string-replace (string-capitalize constant-name) "_" ""))

(declaim (ftype (function (string string &rest java-class)
                          (values java-method-signature &optional))
                get-interface-method))
(defun get-interface-method (interface-name method-name &rest interfaces)
  "return the method description"
  (java-method-signature
   (car (member method-name
                (java-class-methods
                 (car (member interface-name interfaces :key #'java-class-name :test #'string=)))
                :key (compose #'java-method-name #'java-method-signature)
                :test #'string=))))

(declaim (ftype (function ((or keyword null))
                          (values string &optional))
                generate-modifier-string))
(defun generate-modifier-string (modifier)
  "converts a modifier keyword in string"
  (if (null modifier)
      ""
      (string-downcase (symbol-name modifier))))

(declaim (ftype (function (java-field)
                          (values (list-of string) &optional))
                generate-field-declaration))
(defun generate-field-declaration (field)
  (with-accessors ((modifiers java-field-modifiers)
                   (type java-field-type)
                   (name java-field-name)
                   (description-lines java-field-description-lines)
                   (initialization java-field-initialization)) field
    `(,@(when description-lines
              `("/**"
                ,@(mapcar (curry #'format nil " * ~A")
                          description-lines)
                " */"))
        ,(format nil "~{~A ~}~A ~A~@[ = ~A~];"
                 (mapcar #'generate-modifier-string modifiers)
                 (resolve-type-name type t)
                 (generate-member-name name)
                 initialization))))

(declaim (ftype (function (java-method-signature)
                          (values string &optional))
                generate-method-signature))
(defun generate-method-signature (signature)
  "generates the method signature"
  (with-accessors ((modifiers java-method-modifiers)
                   (return-type java-method-return-type)
                   (name java-method-name)
                   (arguments java-method-arguments)
                   (generics java-method-generics)
                   (throws java-method-throws))
      signature
    (format nil "~{~A ~}~@[~A ~]~@[~A ~]~A(~{~{final ~A ~A~}~^, ~})~@[ throws ~{~A~^, ~}~]"
            (mapcar #'generate-modifier-string modifiers)
            (when generics (generate-generics generics))
            (when return-type (resolve-type-name return-type t))
            name arguments throws)))

(deftype-list-of t)
(deftype-list-of list)
(declaim (ftype (function (string
                           (list-of string) (or t (list-of t))
                           (or string java-method-signature) &optional (list-of list))
                          (values string &optional))
                generate-method-call))
(defun generate-method-call (instance arguments casts
                             signature/name &optional formal-arguments)
  "generates the method signature"
  (let* ((name             (if (java-method-signature-p signature/name)
                               (java-method-name signature/name)
                               signature/name))
         (formal-arguments (if (java-method-signature-p signature/name)
                               (java-method-arguments signature/name)
                               formal-arguments))
         (casts            (or (and (listp casts) casts)
                               (make-list (length arguments) :initial-element casts)))
         (arguments        (progn
                             (assert (= (length arguments) (length formal-arguments)))
                             (mapcar (lambda (argument formal-argument cast)
                                       (if cast
                                           (format nil "(~A)~A"
                                                   (car formal-argument)
                                                   argument)
                                           argument))
                                     arguments formal-arguments casts))))
    (format nil "~@[~A.~]~A(~{~A~^, ~})"
            instance name arguments)))

(declaim (ftype (function (java-method &optional t t)
                          (values (list-of string) &optional))
                generate-method))
(defun generate-method (method &optional no-new-line no-semi-colon)
  "generates the declaration of a method"
  (with-accessors ((signature java-method-signature)
                   (description-lines java-method-description-lines)
                   (argument-comments java-method-argument-comments)
                   (return-comment java-method-return-comment)
                   (throws-comments java-method-throws-comments))
      method
    `(,@(when (or description-lines argument-comments return-comment throws-comments)
              `("/**"
                ,@(mapcar (lambda (line)
                            (format nil " * ~A" line))
                          description-lines)
                ,@(when (or argument-comments return-comment throws-comments) '(" *"))
                ,@(mapcar (lambda (line)
                            (format nil " * @param ~A" line))
                          argument-comments)
                ,@(when return-comment `(,(format nil " * @return ~A" return-comment)))
                ,@(mapcar (lambda (line)
                            (format nil " * @throws ~A" line))
                          throws-comments)
                " */"))
        ,@(let ((signature (generate-method-signature signature)))
               `(,(if no-semi-colon
                      signature
                      (format nil "~A;" signature))))
        ,@(unless no-new-line '("")))))

(declaim (ftype (function (java-method &optional t)
                          (values (list-of string) &optional))
                generate-method-with-body))
(defun generate-method-with-body (method &optional no-new-line)
  "generates the declaration of a method"
  (with-accessors ((body java-method-body)) method
    (let ((abstract-p (eq body :abstract)))
      (append
       (generate-method method t (not abstract-p))
       (unless abstract-p
         `("{"
           ,@(mapcar (lambda (line)
                       (format nil "    ~A" line))
                     body)
           "}"))
       (unless no-new-line '(""))))))

(declaim (ftype (function ((list-of string))
                          (values (list-of string) &optional))
                generate-constants))
(defun generate-constants (constants)
  "generates enum constants"
  (let* ((constants (mapcar (curry #'format nil "~A,")
                            constants))
         (last-constant (lastcar constants)))
    (setf (aref last-constant (- (length last-constant) 1)) #\;) ; replace the #\, after the last constant by a #\;
    constants))

(declaim (ftype (function ((list-of java-method) list (list-of string))
                          (values (list-of java-method) &optional))
                generate-factory-methods))
(defun generate-factory-methods (methods content skip)
  "generates the methods data for a factory"
  (assert (eq (first content) :interface-list))
  (append methods
          (let ((content (remove-if (lambda (line)
                                      (member (first line) skip :test #'string=))
                                    (second content))))
            (mapcar (lambda (line)
                      (let* ((class     (first  line))
                             (generics  (second line))
                             (%return   (format nil "~A~A"
                                                class
                                                (generate-generics-instantiation generics)))
                             (name      (format nil "create~A" class)))
                        (create-whole-java-method
                         ('(:public) %return name () generics)
                         :abstract
                         ())))
                    content))))

(declaim (ftype (function (list &optional java-type)
                          (values (list-of java-method) &optional))
                generate-visitor-enums-methods))
(defun generate-visitor-enums-methods (content &optional (%return (create-java-type nil "R")))
  "generates the list of methods corresponding to each enum-constant"
  (let ((enum-data (mapcar (lambda (enum-constant)
                             (cons (first enum-constant)
                                   (car (member (second enum-constant)
                                                (third content)
                                                :key #'first
                                                :test #'string=))))
                           (lastcar (second content)))))
    (let ((generics    (mapcar (compose #'second #'cdr)
                               enum-data))
          (names       (mapcar (compose #'generate-method-name #'first)
                               enum-data))
          (arguments   (mapcar (lambda (enum-constant)
                                 `((
                                    ,(format nil "~A~A"
                                             (first (cdr enum-constant))
                                             (generate-generics-instantiation (second (cdr enum-constant))))
                                    ,"t")
                                   ("P" "p")))
                               enum-data)))
      (mapcar (lambda (enum-constant generics name arguments)
                (cons (first enum-constant)
                      (create-whole-java-method
                       ('(:public) %return (format nil "visit~A" name) arguments generics)
                       :abstract
                       ())))
              enum-data generics names arguments))))

(declaim (ftype (function ((list-of java-method) list (list-of string) &optional java-type)
                          (values (list-of java-method) &optional))
                generate-visitor-methods))
(defun generate-visitor-methods (methods content skip &optional (%return (create-java-type nil "R")))
  "generates the methods data for a visitor"
  (declare
   (ignore skip))
  (assert (member (first content) '(:interface-list :enum)))
  (if (eq (first content) :interface-list)
      '(((nil "" "TODO : the generation of the interface of a visitor from a list of interfaces is not implemented yet" nil)
         nil))
      ;; (eq (first content) :enum)
      (append methods
              (mapcar #'cdr
                      (generate-visitor-enums-methods content %return)))))

(deftype-list-of java-class)
(declaim (ftype (function ((list-of java-import) (list-of java-class) &optional (or string null))
                          (values (list-of java-import) &optional))
                generate-summary-imports))
(defun generate-summary-imports (imports interfaces &optional content-package)
  "generates the list of imports for a summary"
  (if content-package
      (append imports
              (when imports '(nil nil))
              (mapcar (lambda (line content-package)
                        (format nil "~A.~A" content-package (java-class-name line)))
                      interfaces (circular-list content-package)))
      imports))

(declaim (ftype (function ((list-of java-field))
                          (values (list-of string) &optional))
                generate-fields))
(defun generate-fields (fields)
  (let ((fields (mapcar #'generate-field-declaration fields)))
    (cdr (intersperse "" fields #'cons))))

;; Big generating functions

(declaim (ftype (function (java-class string &optional string
                                      (function (stream
                                                 string string string string
                                                 (list-of java-import)
                                                 (list-of string)
                                                 string string string string
                                                 (list-of string))
                                                *)
                                      t (or string null)
                                      (function (java-method &optional t)
                                                (list-of string))))
                generate-interface))
(defun generate-interface (class package
                           &optional (directory ".") (output-function #'write-class-code)
                           implementation-p ipackage (generate-method #'generate-method))
  (declare  )
  "/!\\ DEPRECATED /!\\ use generate-class instead
   generates a source file, fulfilling an interface template"
  (with-accessors ((modifiers java-class-modifiers)
                   (name java-class-name)
                   (generics java-class-generics)
                   (extends java-class-implements)
                   (imports java-class-imports)
                   (created java-class-created)
                   (author java-class-author)
                   (description-lines java-class-description-lines)
                   (fields java-class-fields)
                   (methods java-class-methods)
                   (impl-file-helper java-class-impl-file-helper))
      class
    (let* ((iname name)
           (name (if implementation-p
                     (subseq name 1) ;; remove the leading #\I
                     name))
           (output-file-name (format nil "~A/~A/~A.java"
                                     directory
                                     (string-replace package "." "/")
                                     name)))
      (ensure-directories-exist output-file-name)
      (with-open-file (output output-file-name :direction :output :if-exists :rename)
        (funcall output-function output ""
                 author created package
                 (if implementation-p
                     `(,(format nil "~A.~A" ipackage iname) ,@imports)
                     imports)
                 (generate-description description-lines
                                       (when implementation-p
                                         `(,ipackage . ,iname)))
                 (format nil "~{~A~^ ~}" (mapcar #'generate-modifier-string modifiers))
                 name
                 (generate-generics generics)
                 (generate-extends nil (if implementation-p `(,iname) extends))
                 (append
                  (let ((fields  (generate-fields fields))
                        (methods (mappend generate-method methods)))
                    (if (and fields methods)
                        `(,@fields "" "" ,@methods)
                        (or fields methods)))
                  (when (and implementation-p impl-file-helper)
                    (get-file-lines impl-file-helper)))))))
  (values))

(declaim (ftype (function ((list-of java-class) string string
                           (function (stream
                                      string string string string
                                      (list-of java-import)
                                      (list-of string)
                                      string string string string
                                      (list-of string))
                                     *)
                           &optional t (or string null)
                           (function (java-method) (list-of string)))
                          (values &optional))
                generate-interfaces))
(defun generate-interfaces (interfaces package directory output-function
                            &optional implementation-p ipackage (generate-method #'generate-method))
  "generates the source files, fulfilling an interface template"
  (if *debug-p* (format nil "[I/C] package : ~A~%dir : ~A~%impl : ~A~%input : ~A~%"
                        package directory implementation-p output-function))
  (mapcar (rcurry #'generate-interface package directory output-function
                  implementation-p ipackage generate-method)
          interfaces)
  (values))

(declaim (ftype (function (java-enum string &optional (or license null)
                                     string
                                     (function (stream
                                                string string string string
                                                (list-of java-import)
                                                (list-of string)
                                                string string string string
                                                (list-of string))
                                               *))
                          (values &optional))
                generate-enum))
(defun generate-enum (enum package
                      &optional license (directory ".") (output-function #'write-class-code))
  "generates an enum source file, fulfilling an interface template"
  (with-accessors ((name java-enum-name)
                   (created java-enum-created)
                   (author java-enum-author)
                   (description-lines java-enum-description-lines)
                   (constants java-enum-constants))
      enum
    (let ((output-file-name (format nil "~A/~A/~A.java"
                                    directory
                                    (string-replace package "." "/")
                                    name))
          (license-text     (when license (generate-license-text license author))))
      (ensure-directories-exist output-file-name)
      (with-open-file (output output-file-name :direction :output :if-exists :rename)
        (funcall output-function output license-text
                 author created package () description-lines
                 "public enum" name "" ""
                 (generate-constants constants)))))
  (values))

(deftype-list-of java-enum)
(declaim (ftype (function ((list-of java-enum) string string
                           (function (stream
                                      string string string string
                                      (list-of java-import)
                                      (list-of string)
                                      string string string string
                                      (list-of string))
                                     *))
                          (values &optional))
                generate-enums))
(defun generate-enums (enums package directory output-function)
  "generates the enum source files, fulfilling an interface template"
  (if *debug-p* (format nil "[E] package : ~A~%dir : ~A~%input : ~A~%"
                        package directory output-function))
  (mapcar
   (lambda (enum)
     (apply #'generate-enum
            `(,@enum
              ,package ,directory ,output-function)))
   enums)
  (values))

(declaim (ftype (function (list list string
                                (function ((list-of java-method)
                                           list
                                           (list-of string))
                                          (list-of java-method))
                                &optional string
                                (function (stream
                                           string string string string
                                           (list-of java-import)
                                           (list-of string)
                                           string string string string
                                           (list-of string))
                                          *)
                                string t)
                          (values &optional))
                generate-summary))
(defun generate-summary (data content package generate-methods
                         &optional (directory ".") (output-function #'write-class-code)
                         (cpackage package) implementation-p)
  "generates the summary source file, fulfilling an interface template"
  (if *debug-p* (format t "~A~%~A~%" package cpackage))
  (destructuring-bind (name generics imports created author description skip methods) data
    (let ((imports (generate-summary-imports imports
                                             (lastcar content)
                                             (unless (string= package cpackage)
                                               cpackage)))
          (methods (funcall generate-methods methods content skip)))
      (generate-interface (create-java-class (if implementation-p '(:public :interface) '(:class))
                                             name generics nil () imports created
                                             author description nil methods)
                          package directory output-function implementation-p cpackage)))
  (values))

(declaim (ftype (function (list list &optional string)
                          (values &optional))
                generate-visitor-switch-method))
(defun generate-visitor-switch-method (data content &optional (directory "."))
  "generate the method responsible to call the good visit* Method in the visitor"
  (assert (eq (first content) :enum))
  (let* ((generic-list (append '(("R") ("P"))
                               (second data)))
         (visitor-name (format nil "~A~A"
                               (first data)
                               (generate-generics-instantiation generic-list)))
         (get-enum-method (apply #'get-interface-method
                                 (append
                                  (fifth (second content))
                                  (third content)))))
    (let ((method-signature (generate-method-signature
                             (create-java-method-signature '(:public)
                                                           "R"
                                                           "accept"
                                                           `((,visitor-name "v") ("P" "p"))
                                                           generic-list)))
          (get-enum-method-call (generate-method-call "this" () nil get-enum-method))
          (visitor-methods (mapcar (lambda (method-data)
                                     `(,(car method-data)
                                        ,(generate-method-call "v" '("this" "p") '(t nil)
                                                               (java-method-signature (cdr method-data)))))
                                   (generate-visitor-enums-methods content)))
          (output-file-name (format nil "~A/visitor-accept.java.code"
                                    directory)))
      (ensure-directories-exist output-file-name)
      (with-open-file (output output-file-name :direction :output :if-exists :supersede)
        (format output "~A~%{~%    switch (~A)~%    {~{~{~&        case ~A :~%            return ~A;~}~}~&    }~&}~&"
                method-signature get-enum-method-call visitor-methods))))
  (values))

(declaim (ftype (function (java-class string &optional (or license null)
                                      string
                                      (function (stream
                                                 string string string string
                                                 (list-of java-import)
                                                 (list-of string)
                                                 string string string string
                                                 (list-of string))
                                                *)
                                      (function (java-method &optional t)
                                                (list-of string))))
                generate-class))
(defun generate-class (class package
                       &optional license (directory ".")
                       (output-function #'write-class-code)
                       (generate-method #'generate-method))
  "generates a source file, fulfilling an interface template"
  (with-accessors ((modifiers java-class-modifiers)
                   (name java-class-name)
                   (generics java-class-generics)
                   (extends java-class-extends)
                   (implements java-class-implements)
                   (imports java-class-imports)
                   (created java-class-created)
                   (author java-class-author)
                   (description-lines java-class-description-lines)
                   (fields java-class-fields)
                   (methods java-class-methods)
                   (impl-file-helper java-class-impl-file-helper))
      class
    (let ((output-file-name (format nil "~A/~A/~A.java"
                                    directory (substitute #\/ #\. package :test #'char=) name))
          (license-text     (when license (generate-license-text license author))))
      (ensure-directories-exist output-file-name)
      (with-open-file (output output-file-name :direction :output :if-exists :rename)
        (funcall output-function output license-text
                 author created package imports description-lines
                 (format nil "~{~A~^ ~}" (mapcar #'generate-modifier-string modifiers))
                 name
                 (generate-generics generics)
                 (generate-extends extends implements
                                   (member :interface modifiers))
                 (append
                  (let ((fields  (generate-fields fields))
                        (methods (mappend generate-method methods)))
                    (if (and fields methods)
                        (append fields '("" "") methods)
                        (or fields methods)))
                  (when impl-file-helper (get-file-lines impl-file-helper)))))))
  (values))

(declaim (ftype (function (list string (or license null) string
                                (function (stream
                                           string string string string
                                           (list-of java-import)
                                           (list-of string)
                                           string string string string
                                           (list-of string))
                                          *)
                                &optional
                                (function (java-method &optional t)
                                          (list-of string)))
                          (values &optional))
                generate-classes))
(defun generate-classes (interfaces package license directory output-function
                         &optional (generate-method #'generate-method))
  "generates the source files, fulfilling an interface template"
  (mapcar (rcurry #'generate-class package
                  license directory output-function generate-method)
          interfaces)
  (values))
