/**
 * This package represent a versioned file system (FS). This system can be a CVS
 * repository, or any other versioned FS like an SVN repository (not implemented
 * for the moment).<br>
 * <br>
 * Repository structure is modelized with a composite pattern. See
 * {@linkplain IVerFsElement}, {@linkplain IVerFsComplexElement},
 * {@linkplain IVerFsSimpleRevision}.<br>
 * Each revision ({@linkplain IVerFsSimpleRevision}) have several flags 
 */

package jct.test.rsc.snpsht.verfilesystem;