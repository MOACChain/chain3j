Command Line Tools
==================

A chain3j fat jar is distributed with each release providing command line tools. The command line
allow you to use some of the functionality of chain3j from your terminal:

These tools provide:

- Wallet creation
- Wallet password management
- Mc transfer from one wallet to another
- Generation of Solidity smart contract wrappers

The command line tools can be obtained as a zipfile/tarball from the
`releases <https://github.com/chain3j/chain3j/releases/latest>`_ page of the project repository, under
the **Downloads** section, or for OS X users via
`Homebrew <https://github.com/chain3j/homebrew-chain3j>`_, or for Arch linux users via the
`AUR <https://aur.archlinux.org/packages/chain3j/>`_.

.. code-block:: bash

   brew tap chain3j/chain3j
   brew install chain3j

To run via the zipfile, simply extract the zipfile and run the binary:

.. code-block:: console 

   $ unzip chain3j-<version>.zip
      creating: chain3j-3.0.0/lib/
     inflating: chain3j-3.0.0/lib/core-1.0.2-all.jar
      creating: chain3j-3.0.0/bin/
     inflating: chain3j-3.0.0/bin/chain3j
     inflating: chain3j-3.0.0/bin/chain3j.bat
   $ ./chain3j-<version>/bin/chain3j

                 _      _____ _     _
                | |    |____ (_)   (_)
   __      _____| |__      / /_     _   ___
   \ \ /\ / / _ \ '_ \     \ \ |   | | / _ \
    \ V  V /  __/ |_) |.___/ / | _ | || (_) |
     \_/\_/ \___|_.__/ \____/| |(_)|_| \___/
                            _/ |
                           |__/

   Usage: chain3j version|wallet|solidity ...


Wallet tools
------------

To generate a new Moac wallet:

.. code-block:: bash

   $ chain3j wallet create

To update the password for an existing wallet:

.. code-block:: bash

   $ chain3j wallet update <walletfile>

To send Mc to another address:

.. code-block:: bash

   $ chain3j wallet send <walletfile> 0x<address>|<ensName>

When sending Mc to another address you will be asked a series of questions before the
transaction takes place. See the below for a full example

The following example demonstrates using chain3j to send Mc to another wallet.

.. code-block:: console

   $ ./chain3j-<version>/bin/chain3j wallet send <walletfile> 0x<address>|<ensName>

                 _      _____ _     _
                | |    |____ (_)   (_)
   __      _____| |__      / /_     _   ___
   \ \ /\ / / _ \ '_ \     \ \ |   | | / _ \
    \ V  V /  __/ |_) |.___/ / | _ | || (_) |
     \_/\_/ \___|_.__/ \____/| |(_)|_| \___/
                            _/ |
                           |__/

   Please enter your existing wallet file password:
   Wallet for address 0x19e03255f667bdfd50a32722df860b1eeaf4d635 loaded
   Please confirm address of running Moac client you wish to send the transfer request to [http://localhost:8545/]:
   Connected successfully to client: Geth/v1.4.18-stable-c72f5459/darwin/go1.7.3
   What amound would you like to transfer (please enter a numeric value): 0.000001
   Please specify the unit (mc, sha, ...) [mc]:
   Please confim that you wish to transfer 0.000001 mc (1000000000000 sha) to address 0x9c98e381edc5fe1ac514935f3cc3edaa764cf004
   Please type 'yes' to proceed: yes
   Commencing transfer (this may take a few minutes)...................................................................................................................$

   Funds have been successfully transferred from 0x19e03255f667bdfd50a32722df860b1eeaf4d635 to 0x9c98e381edc5fe1ac514935f3cc3edaa764cf004
   Transaction hash: 0xb00afc5c2bb92a76d03e17bd3a0175b80609e877cb124c02d19000d529390530
   Mined block number: 1849039


Solidity smart contract wrapper generator
------------------------------------------

Please refer to :ref:`smart-contract-wrappers`.
