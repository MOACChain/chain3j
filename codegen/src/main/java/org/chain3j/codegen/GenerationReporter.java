package org.chain3j.codegen;

/**
 * Can be used to provide report about a code generation process.
 */
interface GenerationReporter {
    void report(String msg);
}
