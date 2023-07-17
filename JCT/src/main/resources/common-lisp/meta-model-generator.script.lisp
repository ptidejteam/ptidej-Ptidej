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
;;  -mmi               : Meta-model interfaces
;;  -mmc               : Meta-model implementations
;;  -fi                : Factory interface
;;  -fc                : Factory implementation
;;  -v                 : Visitor interface
;;  -e                 : Kind Enum
;;  -l                 : License to use (default BSD-4)
;;
;; => The last command line argument is used as the name of the input data file <=
;;
;; TODO ::

(cl:require :meta-model-generator)

(cl:defpackage #:meta-model-script
  (:use #:cl #:code-generation #:script-utility #:license-generation
        #:meta-model-generation)
  (:import-from #:alexandria
                #:curry #:compose))

(cl:in-package #:meta-model-script)

(with-debug-vars code-generation:*debug-p*)

(load (get-last-argument))              ; data to fulfill templates

(defparameter *license* (get-license-from-name (get-argument "-l" :default-value "3-clause BSD")))

(when-argument "-mmi"
  (generate-classes (mapcar
                     #'generate-constituent-interface
                     (remove-if #1=(compose (curry #'member :impl-only) #'mm-constituent-flags)
                                (flatten *constituents* #1# #2=(compose (curry #'member :intrf-only) #'mm-constituent-flags))))
                    (get-package)
                    *license*
                    (get-directory)
                    (get-model "write-class-code")))

(when-argument "-mmc"
  (generate-classes (mapcar
                     (curry #'generate-constituent-implementation (get-package))
                     (remove-if #2# (flatten *constituents* #2# #2#)))
                    (get-package t)
                    *license*
                    (get-directory)
                    (get-model "write-class-code")
                    #'generate-method-with-body))

(when-argument "-fi"
  (generate-class (generate-factory-interface *factory*
                                              (remove-if (compose
                                                          (disjoin
                                                           (curry #'member :abstract)
                                                           (curry #'member :impl-only)
                                                           (curry #'member :intrf-only))
                                                          #'mm-constituent-flags)
                                                         *constituents*))
                  (get-package)
                  *license*
                  (get-directory)
                  (get-model "write-class-code")))

(when-argument "-fc"
  (generate-class (generate-factory-implementation
                   (get-package)
                   *factory*
                   (remove-if (compose
                               (disjoin
                                (curry #'member :abstract)
                                (curry #'member :impl-only)
                                (curry #'member :intrf-only))
                               #'mm-constituent-flags)
                              *constituents*))
                  (get-package t)
                  *license*
                  (get-directory)
                  (get-model "write-class-code")))

(when-argument "-v"
  (generate-class (generate-visitor-interface *visitor*
                                              (remove-if (compose
                                                          (disjoin
                                                           (curry #'member :abstract)
                                                           (curry #'member :impl-only)
                                                           (curry #'member :intrf-only))
                                                          #'mm-constituent-flags)
                                                         *constituents*))
                  (get-package)
                  *license*
                  (get-directory)
                  (get-model "write-class-code")))

(when-argument "-e"
  (let ((*kind-enum* (generate-actual-enum-kind *kind-enum* *constituents*)))
    (generate-enum *kind-enum*
                   (get-package)
                   *license*
                   (get-directory)
                   (get-model "write-class-code"))))

#+(or) (progn
         (generate-classes (mapcar
                            #'generate-constituent-interface
                            (remove-if #1=(compose (curry #'member :impl-only) #'mm-constituent-flags)
                                       (flatten *constituents* #1# #2=(compose (curry #'member :intrf-only) #'mm-constituent-flags))))
                           "jct.kernel"
                           #3=(get-license-from-name "3-clause BSD")
                           "/home/swoog/docs/boulot/UdeM/Maitrise/JCT/src/" #+(or) "/tmp/tmp/output/interfaces/"
                           #'write-class-code)

         (generate-classes (mapcar
                            (curry #'generate-constituent-implementation "jct.kernel")
                            (remove-if #2# (flatten *constituents* #2# #2#)))
                           "jct.kernel.impl"
                           #3#
                           "/home/swoog/docs/boulot/UdeM/Maitrise/JCT-Impl/src/" #+(or) "/tmp/tmp/output/impl/"
                           #'write-class-code
                           #'generate-method-with-body)

         (generate-class (generate-factory-interface
                          *factory*
                          (remove-if (compose
                                      (disjoin
                                       (curry #'member :abstract)
                                       (curry #'member :impl-only)
                                       (curry #'member :intrf-only))
                                      #'mm-constituent-flags)
                                     *constituents*))
                         "jct.kernel"
                         #3#
                         "/home/swoog/docs/boulot/UdeM/Maitrise/JCT/src/" #+(or) "/tmp/tmp/output/interfaces/")

         (generate-class (generate-factory-implementation
                          "jct.kernel"
                          *factory*
                          (remove-if (compose
                                      (disjoin
                                       (curry #'member :abstract)
                                       (curry #'member :impl-only)
                                       (curry #'member :intrf-only))
                                      #'mm-constituent-flags)
                                     *constituents*))
                         "jct.kernel.impl"
                         #3#
                         "/home/swoog/docs/boulot/UdeM/Maitrise/JCT-Impl/src/" #+(or) "/tmp/tmp/output/impl/"
                         #'write-class-code
                         #'generate-method-with-body)

         (generate-class (generate-visitor-interface *visitor*
                                                     (remove-if (compose
                                                                 (disjoin
                                                                  (curry #'member :abstract)
                                                                  (curry #'member :impl-only)
                                                                  (curry #'member :intrf-only))
                                                                 #'mm-constituent-flags)
                                                                *constituents*))
                         "jct.kernel"
                         #3#
                         "/home/swoog/docs/boulot/UdeM/Maitrise/JCT/src/" #+(or) "/tmp/tmp/output/interfaces/")
         (let ((*kind-enum* (generate-actual-enum-kind *kind-enum* *constituents*)))
           (generate-enum *kind-enum*
                          "jct.kernel"
                          #3#
                          "/home/swoog/docs/boulot/UdeM/Maitrise/JCT/src/" #+(or) "/tmp/tmp/output/interfaces/"
                          #'write-class-code)))
