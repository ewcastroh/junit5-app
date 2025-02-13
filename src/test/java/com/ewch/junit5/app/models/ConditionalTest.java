package com.ewch.junit5.app.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

public class ConditionalTest {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testOnlyWindows() {}

    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void testOnlyMacOSAndLinux() {}

    @Test
    @DisabledOnOs(OS.MAC)
    void testNoMacOS() {}

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testOnlyJava8() {}

    @Test
    @EnabledOnJre(JRE.JAVA_21)
    void testOnlyJava21(){}

    @Test
    @DisabledOnJre(JRE.JAVA_21)
    void testNoOnlyJava21() {}

    @Test
    void printSystemProperties() {
        System.getProperties().list(System.out);
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "21.*.*")
    void testJavaVersion() {}

    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = "32")
    void testNo64Bits() {}

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = "aarch64")
    void test64Bits() {}

    @Test
    @EnabledIfSystemProperty(named = "user.name", matches = "eimer")
    void testOnlyEimer() {}

    @Test
    @DisabledIfSystemProperty(named = "user.name", matches = "eimer")
    void testNoEimer() {}

    @Test
    @EnabledIfSystemProperty(named ="ENV", matches = "dev")
    void testDev() {}

    @Test
    void printEnvironmentVariables() {
        System.getenv().forEach((k, v) -> System.out.println(k + "=" + v));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USER", matches = "eimer")
    void testUserFromEnvironmentVariable() {}

    @Test
    @DisabledIfEnvironmentVariable(named = "USER", matches = "eimer")
    void testNoUserFromEnvironmentVariable() {}

    @Test
    @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "8")
    void testNoNumberOfProcessors() {}

    @Test
    @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
    void testDevEnvironment() {}

    @Test
    @DisabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "prod")
    void testNoProdEnvironment() {}
}
