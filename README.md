PLAST - Java project
====================

This project contains:
----------------------

1. a sample Java application to illustrate the use of PLAST native binary from a Java
   application;
   
2. some Fasta sample files to use as query and subject files to run PLAST comparison jobs; 

3. PLAST pre-compiled native libraries for Linux, MacOS and Windows;

4. the "plast.jar" library required to develop Java PLAST-based applications;

5. an Ant "build.xml" file to handle the project; type "ant help" in your terminal for more information.

6. ".classpath" and ".project" files to import this project into Eclipse IDE. 


Requirements
------------

1. Oracle Java SDK 1.7 or above; avoid using OpenJDK, etc.
2. Apache Ant 1.9.1 or above.


Testing software
----------------

This project is self contained, so you can easily test it on the command-line. Using a terminal, type in the following commands:

    ant cmp
    ant run


About PLAST binary
------------------

Inria/Genscale Team provide PLAST binaries as follows:

+ MacOSX PLAST binary: created on OS 10.7.5 (gcc 4.2) 

+ Linux PLAST binary: created on CentOS 6.3 (gcc 4.2.1 ; kernel 2.6.32-279.el6.x86_64 ; GLIBCXX_3.4.13 ; libstdc++.so.6)  

+ Windows PLAST binary: created on Windows 7.1 Pro 64bits (MinGW-64/gcc 5.2)

Provided PLAST binaries should work on more recent releases of these OS. However, depending on your system, the provided binaries may not work. In such a case, please download source code and compile it on your system. 

Compiling PLAST: [http://plast.gforge.inria.fr/docs/cpp/index.html#compile_plast](http://plast.gforge.inria.fr/docs/cpp/index.html#compile_plast)


Developer documentation
-----------------------

See [https://project.inria.fr/plast/developer-guide/](https://project.inria.fr/plast/developer-guide/) for general documentation.

Plast Java API: [http://plast.gforge.inria.fr/docs/java/](http://plast.gforge.inria.fr/docs/java/).

License
-------

PLAST is free software; you can redistribute it and/or modify it under the Affero GPL v3 
license. See http://www.gnu.org/licenses/agpl-3.0.en.html

About Github mirror
-------------------

PLAST project resides at two locations:

* [Inria Forge](https://gforge.inria.fr/): the official git repository
* Github: this is a mirror of the Inria Forge repository

The Github repository is auto-updated from Inria GForge. So all commits have to be done by authorized users/developers on the Inria Forge. Otherwise, consider using "Github/pull request" for any update to be done on PLAST.