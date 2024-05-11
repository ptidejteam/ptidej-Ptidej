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

(cl:defpackage #:mm-script-utility
  (:use #:cl #:script-utility)
  (:export #:get-package #:get-directory #:get-model))

(cl:in-package #:mm-script-utility)

(declaim (ftype (function (&optional t)
                          (values string &optional))
                get-package))
(defun get-package (&optional implements-p)
  "retreive the name of the package"
  (let ((package-name (get-argument "-p" :default-value "")))
    (if implements-p
        (format nil "~A.impl" package-name)
        package-name)))

(declaim (ftype (function ()
                          (values string &optional))
                get-directory))
(defun get-directory ()
  "retrieve the root directory of the source tree"
  (get-argument "-d" :default-value "."))

(declaim (ftype (function (string)
                          (values (function * *) &optional))
                get-model))
(defun get-model (default-value)
  "retrieve the name of the model function"
  (fdefinition (find-symbol (string-upcase
                             (get-argument "-m" :default-value default-value)))))
