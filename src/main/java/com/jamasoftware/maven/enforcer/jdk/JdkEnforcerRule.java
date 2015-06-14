package com.jamasoftware.maven.enforcer.jdk;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.model.Profile;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

import java.util.List;
import java.util.Properties;

import static java.lang.String.format;

public class JdkEnforcerRule implements EnforcerRule {

    private static final String ORACLE_CORPORATION = "Oracle Corporation";
    private static final String JAVA_SE = "Java(TM) SE Runtime Environment";
    private static final String VENDOR_PROPERTY_REGEX = "^java\\.(.+\\.)vendor$";
    private static final String RUNTIME_PROPERTY_REGEX = "^java\\.runtime\\.name$";

    @Override
    public void execute(EnforcerRuleHelper helper) throws EnforcerRuleException {
        Properties properties = System.getProperties();
        for (String propertyName : properties.stringPropertyNames()) {
            String propertyValue = properties.getProperty(propertyName);
            if (isVendor(propertyName) && isNotOracle(propertyValue)) {
                throwVendorException(propertyName, propertyValue);
            }
            if (isRuntime(propertyName) && isNotJavaSe(propertyValue)) {
                throwRuntimeException(propertyName, propertyValue);
            }
        }

        Log log = helper.getLog();
        log.info("your JDK is approved for Jama usage");
    }

    private void throwRuntimeException(String propertyName, String propertyValue) throws EnforcerRuleException {
        String format = "The property \"%s\" does not reflect the correct Java runtime, should be: \"%s\", was: \"%s\"";
        String message = format(format, propertyName, JAVA_SE, propertyValue);
        throw new EnforcerRuleException(message);
    }

    private void throwVendorException(String propertyName, String propertyValue) throws EnforcerRuleException {
        String format = "The property \"%s\" does not reflect the correct vendor, should be: \"%s\", was: \"%s\"";
        String message = format(format, propertyName, ORACLE_CORPORATION, propertyValue);
        throw new EnforcerRuleException(message);
    }

    private boolean isNotJavaSe(String propertyValue) {
        return !JAVA_SE.equals(propertyValue);
    }

    private boolean isNotOracle(String propertyValue) {
        return !ORACLE_CORPORATION.equals(propertyValue);
    }

    private boolean isRuntime(String propertyName) {
        return propertyName.matches(RUNTIME_PROPERTY_REGEX);
    }

    private boolean isVendor(String propertyName) {
        return propertyName.matches(VENDOR_PROPERTY_REGEX);
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public boolean isResultValid(EnforcerRule cachedRule) {
        return false;
    }

    @Override
    public String getCacheId() {
        return null;
    }

}
