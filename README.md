# voronoi-java [![License](http://img.shields.io/badge/license-MIT-lightgrey.svg?style=flat)][License] [![Build Status](http://img.shields.io/travis/aschlosser/voronoi-java.svg?style=flat)](https://travis-ci.org/aschlosser/voronoi-java)
A lightweight java library for generating 2D Voronoi diagrams using Fortune's Algorithm

## Usage
At a later point in time voronoi-java will be available on maven central. For the time being just clone the repository and install it into your local maven repository (see [Building from Source](#building-from-source)).

## Source Code
The latest source can be found here on [GitHub](https://github.com/aschlosser/voronoi-java). To clone the project:

    git clone git://github.com/aschlosser/voronoi-java.git

Or download the latest [archive](https://github.com/aschlosser/voronoi-java/archive/master.zip).

## Building from Source
This project can be built with the _latest_ [Java Development Kit](http://oracle.com/technetwork/java/javase/downloads) and [Maven](https://maven.apache.org/). The command `mvn package` will build the project and will put the compiled JAR in `target`, and `mvn install` will copy it to your local Maven repository.

## Contributing
Your help is welcome! Just open a pull request with your changes.

## License
voronoi-java is licensed under the [MIT License][License]. Basically, you can do as you please as long as you include the original copyright notice. Please see the `License.md` file for details.

## Credits
Most of the code is derived from the desciptions included in the book 'Computational Geometry: Algorithms and Applications'
by Mark de Berg, Otfried Cheong, Marc van Kreveld and Mark Overmars (ISBN-13: 978-3540779735)

[License]: https://choosealicense.com/licenses/mit/
