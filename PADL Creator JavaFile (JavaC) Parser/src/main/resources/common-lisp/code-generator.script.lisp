#!/usr/bin/sbcl --noinform --script

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

;;  /!\  NO COLLAPSED OPTIONS /!\
;; This script is not able to parse something like -ip instead of -i -p
;;  /!\  NO COLLAPSED OPTIONS /!\

;; Summary of command line options :
;;
;;  --debug             : enable debug facilities
;;  -p  "package name"  : name of the package in which the interfaces will be generated {""}
;;  -d  "directory"     : root directory of the source tree {"."}
;;  -i                  : Interfaces
;;  -c                  : implementations (add .impl to the name of the package)
;;  -m "model function" : model function to use {"write-class-code"}
;;  -e                  : Enum generation
;;  -f                  : Factory interface
;;  -fc                 : Factory implementation
;;  -ip "package name"  : name of package containing the interfaces when generating the factory {-p value}
;;  -v  "enum name"     : Visitor interface
;;  -vs "enum name"     : Visitor accept switch method
;;
;; => The last command line argument is used as the name of the input data file <=
;;
;; TODO ::

(cl:require :meta-model-generator)

(cl:defpackage #:code-generation-script
  (:use #:cl #:alexandria #:code-generation #:script-utility)
  (:export #:get-argument #:get-package #:get-interface-package-for-summary #:get-model
           #:get-directory))

(cl:in-package #:code-generation-script)

(defun get-interface-package-for-summary ()
  "retreive the name of the package containing the interfaces"
  (get-argument "-ip" :default-value (get-package)))

;; Main Program

(with-debug-vars code-generation:*debug-p*)

(load (get-last-argument)) ; meta-data to fulfill templates

(when-argument ("-c" :boolean-p t)
  (generate-interfaces *interfaces*
                       (get-package t)
                       (get-directory)
                       (get-model "write-class-code")
                       t
                       (get-package)))

(when-argument ("-i" :boolean-p t)
  (generate-interfaces *interfaces*
                       (get-package)
                       (get-directory)
                       (get-model "write-class-code")))

(when-argument ("-e" :boolean-p t)
  (generate-enums *enums*
                  (get-package)
                  (get-directory)
                  (get-model "write-class-code")))

(when-argument ("-f" :boolean-p t)
  (generate-summary *factory*
                    `(:interface-list ,*interfaces*)
                    (get-package)
                    #'generate-factory-methods
                    (get-directory)
                    (get-model "write-class-code")
                    (get-interface-package-for-summary)))

(when-argument ("-fc" :boolean-p t)
  (generate-summary *factory*
                    `(:interface-list ,*interfaces*)
                    (get-package t)
                    #'generate-factory-methods
                    (get-directory)
                    (get-model "write-class-code")
                    (get-interface-package-for-summary)
                    t))

(when-argument (visitor-enum "-v")
  (generate-summary *visitor*
                    `(:enum
                      ,(assoc visitor-enum *enums* :test #'string=)
                      ,*interfaces*)
                    (get-package)
                    #'generate-visitor-methods
                    (get-directory)
                    (get-model "write-class-code")
                    (get-interface-package-for-summary)))


(when-argument (visitor-enum "-vs")
  (generate-visitor-switch-method *visitor*
                                  `(:enum
                                    ,(assoc visitor-enum *enums* :test #'string=)
                                    ,*interfaces*)
                                  (get-directory)))
