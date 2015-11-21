package com.vote;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import com.google.common.base.Joiner;
import com.vote.config.Constants;




@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableSpringDataWebSupport
public class Application {
	
	@Inject
	private Environment env;
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	
	@PostConstruct
	public void initApplication() throws IOException {
        if (env.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration");
        } else {
            log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        }
    }
	
	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(Application.class);
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        
        addDefaultProfile(app, source);
		addLiquibaseScanPackages();
		 Environment env = app.run(args).getEnvironment();
	        log.info("Access URLs:\n----------------------------------------------------------\n\t" +
	            "Local: \t\thttp://127.0.0.1:{}\n\t" +
	            "External: \thttp://{}:{}\n----------------------------------------------------------",
	            env.getProperty("server.port"),
	            InetAddress.getLocalHost().getHostAddress(),
	            env.getProperty("server.port"));
	}  
	
	
	
	
	
	
	/**
     * Set a default profile if it has not been set
     */
    private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
        if (!source.containsProperty("spring.profiles.active")) {
            app.setAdditionalProfiles(Constants.SPRING_PROFILE_LOCAL);
        }
    }
	
	/**
     * Set the liquibases.scan.packages to avoid an exception from ServiceLocator.
     */
    private static void addLiquibaseScanPackages() {
        System.setProperty("liquibase.scan.packages", Joiner.on(",").join(
            "liquibase.change", "liquibase.database", "liquibase.parser",
            "liquibase.precondition", "liquibase.datatype",
            "liquibase.serializer", "liquibase.sqlgenerator", "liquibase.executor",
            "liquibase.snapshot", "liquibase.logging", "liquibase.diff",
            "liquibase.structure", "liquibase.structurecompare", "liquibase.lockservice",
            "liquibase.ext", "liquibase.changelog"));
    }
}
