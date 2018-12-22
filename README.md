.. To build this file locally ensure docutils Python package is installed and run:
   $ rst2html.py README.rst README.html

chain3j: Chain3 Java MOAC Ðapp API
==================================

chain3j is a lightweight, highly modular, reactive, type safe Java and Android library for working with
Smart Contracts and integrating with clients (nodes) on the MOAC network:

This allows you to work with the `MOAC <https://www.moac.io/>`_ blockchain, without the
additional overhead of having to write your own integration code for the platform.

Features
--------

- Complete implementation of MOAC's `JSON-RPC <https://github.com/MOACChain/moac-core/wiki/JSON-RPC>`_
  client API over HTTP and IPC

- Auto-generation of Java smart contract wrappers to create, deploy, transact with and call smart
  contracts from native Java code
  (`Solidity <http://solidity.readthedocs.io/en/latest/using-the-compiler.html#using-the-commandline-compiler>`_
  and
- Reactive-functional API for working with filters
- Support for `MOAC gateway <https://gateway.moac.io/>`_, so you don't have to run an MOAC client yourself
- Comprehensive integration tests demonstrating a number of the above scenarios
- Command line tools
- Android compatible

It has five runtime dependencies:

- `RxJava <https://github.com/ReactiveX/RxJava>`_ for its reactive-functional API
- `OKHttp <https://hc.apache.org/httpcomponents-client-ga/index.html>`_ for HTTP connections
- `Jackson Core <https://github.com/FasterXML/jackson-core>`_ for fast JSON
  serialisation/deserialisation
- `Bouncy Castle <https://www.bouncycastle.org/>`_
  (`Spongy Castle <https://rtyley.github.io/spongycastle/>`_ on Android) for crypto
- `Jnr-unixsocket <https://github.com/jnr/jnr-unixsocket>`_ for \*nix IPC (not available on
  Android)

It also uses `JavaPoet <https://github.com/square/javapoet>`_ for generating smart contract
wrappers.

Commercial support and training
-------------------------------

Commercial support and training is available from `moac.io <https://moac.io>`_.


Quickstart
----------

A `chain3j sample project <https://github.com/DavidRicardoWilde/chain3j-Win-Demo>`_ is available that
demonstrates a number of core features of MOAC with chain3j, including:

- Connecting to a node on the MOAC network
- Loading an MOAC keystore file
- Sending Moac from one address to another
- Deploying a smart contract to the network
- Reading a value from the deployed smart contract
- Updating a value in the deployed smart contract
- Viewing an event logged by the smart contract


Getting started
---------------

Typically your application should depend on release versions of chain3j, but you may also use snapshot dependencies
for early access to features and fixes, refer to the  `Snapshot Dependencies`_ section.

| Add the relevant dependency to your project:

Maven
-----

Java 8:

.. code-block:: xml

   <dependency>
     <groupId>org.chain3j</groupId>
     <artifactId>core</artifactId>
     <version>0.1.0</version>
   </dependency>

Android:

.. code-block:: xml

   <dependency>
     <groupId>org.chain3j</groupId>
     <artifactId>core</artifactId>
     <version>0.1.0-android</version>
   </dependency>


Gradle
------

Java 8:

.. code-block:: groovy

   compile ('org.chain3j:core:0.1.0')

Android:

.. code-block:: groovy

   compile ('org.chain3j:core:0.1.0-android')


Start a client
--------------

Start up an MOAC client if you don't already have one running, check
_:

.. code-block:: bash

   $ ./moac --rpcapi personal,mc,net,chain3 --rpc --testnet

.. code-block:: java

   Chain3j chain3 = Chain3j.build(new HttpService("http://gateway.moac.io"));

For further information refer to



Start sending requests
----------------------

To send synchronous requests:

.. code-block:: java

   Chain3j chain3 = Chain3j.build(new HttpService());  // defaults to http://localhost:8545/
   Chain3ClientVersion chain3ClientVersion = chain3.chain3ClientVersion().send();
   String clientVersion = chain3ClientVersion.getChain3ClientVersion();


To send asynchronous requests using a CompletableFuture (Future on Android):

.. code-block:: java

   Chain3j chain3 = Chain3j.build(new HttpService());  // defaults to http://localhost:8545/
   Chain3ClientVersion chain3ClientVersion = chain3.chain3ClientVersion().sendAsync().get();
   String clientVersion = chain3ClientVersion.getChain3ClientVersion();

To use an RxJava Observable:

.. code-block:: java

   Chain3j chain3 = Chain3j.build(new HttpService());  // defaults to http://localhost:8545/
   chain3.chain3ClientVersion().observable().subscribe(x -> {
       String clientVersion = x.getChain3ClientVersion();
       ...
   });

**Note:** for Android use:

.. code-block:: java

   Chain3j chain3 = Chain3jFactory.build(new HttpService());  // defaults to http://localhost:8545/
   ...


IPC
---

chain3j also supports fast inter-process communication (IPC) via file sockets to clients running on
the same host as chain3j. To connect simply use the relevant *IpcService* implementation instead of
*HttpService* when you create your service:

.. code-block:: java

   // OS X/Linux/Unix:
   Chain3j chain3 = Chain3j.build(new UnixIpcService("/path/to/socketfile"));
   ...

   // Windows
   Chain3j chain3 = Chain3j.build(new WindowsIpcService("/path/to/namedpipefile"));
   ...

**Note:** IPC is not currently available on chain3j-android.


Working with smart contracts with Java smart contract wrappers
--------------------------------------------------------------

chain3j can auto-generate smart contract wrapper code to deploy and interact with smart contracts
without leaving the JVM.

To generate the wrapper code, compile your smart contract:

.. code-block:: bash

   $ solc <contract>.sol --bin --abi --optimize -o <output-dir>/

Then generate the wrapper code using chain3j's `Command line tools`_:

.. code-block:: bash

   chain3j solidity generate /path/to/<smart-contract>.bin /path/to/<smart-contract>.abi -o /path/to/src/main/java -p com.your.organisation.name

Now you can create and deploy your smart contract:

.. code-block:: java

   Chain3j chain3 = Chain3j.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");

   YourSmartContract contract = YourSmartContract.deploy(
           <chain3j>, <credentials>,
           GAS_PRICE, GAS_LIMIT,
           <param1>, ..., <paramN>).send();  // constructor params

Alternatively, if you use `MOAC wallet <https://wallet.moac.io//>`_, you can make use of its `.json` output files:

.. code-block:: bash

   # Open MOAC wallet and start a local MOAC node
   # Click CONTRACTS tab and choose the "DEPLOY NEW CONTRACT" button
   # Copy the contract codes to the "SOLIDITY CONTRACT SOURCE CODE"
   # The codes will be auto compiled.

Then generate the wrapper code using chain3j's `Command line tools`_:

.. code-block:: bash

   $ cd /path/to/your/chain3j/java/project
   $ chain3j truffle generate /path/to/<truffle-smart-contract-output>.json -o /path/to/src/main/java -p com.your.organisation.name

Whether using `Truffle` or `solc` directly, either way you get a ready-to-use Java wrapper for your contract.

So, to use an existing contract:

.. code-block:: java

   YourSmartContract contract = YourSmartContract.load(
           "0x<address>|<ensName>", <chain3j>, <credentials>, GAS_PRICE, GAS_LIMIT);

To transact with a smart contract:

.. code-block:: java

   TransactionReceipt transactionReceipt = contract.someMethod(
                <param1>,
                ...).send();

To call a smart contract:

.. code-block:: java

   Type result = contract.someMethod(<param1>, ...).send();

To fine control your gas price:

.. code-block:: java

    contract.setGasProvider(new DefaultGasProvider() {
            ...
            });

For more information refer to `Smart Contracts <http://docs.chain3j.io/smart_contracts.html#solidity-smart-contract-wrappers>`_.


Filters
-------

chain3j functional-reactive nature makes it really simple to setup observers that notify subscribers
of events taking place on the blockchain.

To receive all new blocks as they are added to the blockchain:

.. code-block:: java

   Subscription subscription = chain3j.blockObservable(false).subscribe(block -> {
       ...
   });

To receive all new transactions as they are added to the blockchain:

.. code-block:: java

   Subscription subscription = chain3j.transactionObservable().subscribe(tx -> {
       ...
   });

To receive all pending transactions as they are submitted to the network (i.e. before they have
been grouped into a block together):

.. code-block:: java

   Subscription subscription = chain3j.pendingTransactionObservable().subscribe(tx -> {
       ...
   });

Or, if you'd rather replay all blocks to the most current, and be notified of new subsequent
blocks being created:

.. code-block:: java
   Subscription subscription = catchUpToLatestAndSubscribeToNewBlocksObservable(
           <startBlockNumber>, <fullTxObjects>)
           .subscribe(block -> {
               ...
   });

There are a number of other transaction and block replay Observables described in the
`docs <http://docs.chain3j.io/filters.html>`_.

Topic filters are also supported:

.. code-block:: java

   McFilter filter = new McFilter(DefaultBlockParameterName.EARLIEST,
           DefaultBlockParameterName.LATEST, <contract-address>)
                .addSingleTopic(...)|.addOptionalTopics(..., ...)|...;
   chain3j.mcLogObservable(filter).subscribe(log -> {
       ...
   });

Subscriptions should always be cancelled when no longer required:

.. code-block:: java

   subscription.unsubscribe();

**Note:** filters are not supported on Infura.

For further information refer to `Filters and Events <http://docs.chain3j.io/filters.html>`_ and the
`Chain3jRx <https://github.com/chain3j/chain3j/blob/master/src/core/main/java/org/chain3j/protocol/rx/Chain3jRx.java>`_
interface.


Transactions
------------

chain3j provides support for both working with MOAC wallet files (recommended) and MOAC
client admin commands for sending transactions.

To send Mc to another party using your MOAC wallet file:

.. code-block:: java

   Chain3j chain3 = Chain3j.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
   TransactionReceipt transactionReceipt = Transfer.sendFunds(
           chain3, credentials, "0x<address>|<ensName>",
           BigDecimal.valueOf(1.0), Convert.Unit.MC)
           .send();

Or if you wish to create your own custom transaction:

.. code-block:: java

   Chain3j chain3 = Chain3j.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");

   // get the next available nonce
   McGetTransactionCount mcGetTransactionCount = chain3j.mcGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();
   BigInteger nonce = mcGetTransactionCount.getTransactionCount();

   // create our transaction
   RawTransaction rawTransaction  = RawTransaction.createMcTransaction(
                nonce, <gas price>, <gas limit>, <toAddress>, <value>);

   // sign & send out transaction with EIP155 signature
   byte[] signedMessage = TransactionEncoder.signTxEIP155(rawTransaction, <chainId>, credentials);
   String hexValue = Hex.toHexString(signedMessage);
   McSendTransaction mcSendTransaction = chain3j.SendRawTransaction(hexValue).send();
   // ...

Although it's far simpler using chain3j's `Transfer <https://github.com/chain3j/chain3j/blob/master/core/src/main/java/org/chain3j/tx/Transfer.java>`_
for transacting with Mc.

Using an MOAC client's admin commands (make sure you have your wallet in the client's
keystore):

.. code-block:: java

   Admin chain3j = Admin.build(new HttpService());  // defaults to http://localhost:8545/
   PersonalUnlockAccount personalUnlockAccount = chain3j.personalUnlockAccount("0x000...", "a password").sendAsync().get();
   if (personalUnlockAccount.accountUnlocked()) {
       // send a transaction
   }

Command line tools
------------------

A chain3j fat jar is distributed with each release providing command line tools. The command line
tools allow you to use some of the functionality of chain3j from the command line:

- Wallet creation
- Wallet password management
- Transfer of funds from one wallet to another
- Generate Solidity smart contract function wrappers


Further details
---------------

In the Java 8 build:

- chain3j provides type safe access to all responses. Optional or null responses
  are wrapped in Java 8's
  `Optional <https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html>`_ type.
- Asynchronous requests are wrapped in a Java 8
  `CompletableFutures <https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html>`_.
  chain3j provides a wrapper around all async requests to ensure that any exceptions during
  execution will be captured rather then silently discarded. This is due to the lack of support
  in *CompletableFutures* for checked exceptions, which are often rethrown as unchecked exception
  causing problems with detection. See the
  `Async.run() <https://github.com/chain3j/chain3j/blob/master/core/src/main/java/org/chain3j/utils/Async.java>`_ and its associated
  `test <https://github.com/chain3j/chain3j/blob/master/core/src/test/java/org/chain3j/utils/AsyncTest.java>`_ for details.

In both the Java 8 and Android builds:

- Quantity payload types are returned as `BigIntegers <https://docs.oracle.com/javase/8/docs/api/java/math/BigInteger.html>`_.
  For simple results, you can obtain the quantity as a String via
  `Response <https://github.com/chain3j/chain3j/blob/master/src/main/java/org/chain3j/protocol/core/Response.java>`_.getResult().
- It's also possible to include the raw JSON payload in responses via the *includeRawResponse*
  parameter, present in the
  `HttpService <https://github.com/chain3j/chain3j/blob/master/core/src/main/java/org/chain3j/protocol/http/HttpService.java>`_
  and
  `IpcService <https://github.com/chain3j/chain3j/blob/master/core/src/main/java/org/chain3j/protocol/ipc/IpcService.java>`_
  classes.


Build instructions
------------------

chain3j includes integration tests for running against a live MOAC client. If you do not have a
client running, you can exclude their execution as per the below instructions.


To see the compile options:

.. code-block:: bash

   $ ./gradlew tasks

To run a full build (excluding integration tests):

.. code-block:: bash

   $ ./gradlew check


Sample maven configuration:

.. code-block:: xml

   <repositories>
     <repository>
       <id>sonatype-snasphots</id>
       <name>Sonatype snapshots repo</name>
       <url>https://oss.sonatype.org/content/repositories/snapshots</url>
     </repository>
   </repositories>

Thanks and credits
------------------

- The `Web3j <https://github.com/web3j/web3j>`_ project for the framework
- The `Nethereum <https://github.com/Nethereum/Nethereum>`_ project for the inspiration
- `Othera <https://www.othera.com.au/>`_ for the great things they are building on the platform
- `Finhaus <http://finhaus.com.au/>`_ guys for putting me onto Nethereum
- `bitcoinj <https://bitcoinj.github.io/>`_ for the reference Elliptic Curve crypto implementation
- Everyone involved in the Ethererum project and its surrounding ecosystem
- And of course the users of the library, who've provided valuable input & feedback
