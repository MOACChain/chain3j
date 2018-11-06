Developer Guide
===============

Building chain3j
--------------

chain3j includes integration tests for running against a live Moac client. If you do not have a
client running, you can exclude their execution as per the below instructions.

To run a full build (excluding integration tests):

.. code-block:: bash

   $ ./gradlew check


To run the integration tests:

.. code-block:: bash

   $ ./gradlew  -Pintegration-tests=true :integration-tests:test


Generating documentation
------------------------

chain3j uses the `Sphinx <http://www.sphinx-doc.org/en/stable/>`_ documentation generator.

All documentation (apart from the project README.md) resides under the
`/docs <https://github.com/chain3j/chain3j/tree/master/docs>`_ directory.

To build a copy of the documentation, from the project root:

.. code-block:: bash

   $ cd docs
   $ make clean html

Then browse the build documentation via:

.. code-block:: bash

   $ open build/html/index.html
