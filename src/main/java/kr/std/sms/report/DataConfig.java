package kr.std.sms.report;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import kr.std.sms.report.alvfile.ReportFileCollect;

@Configuration
@PropertySource(value = "application.properties")
public class DataConfig {
	
	private static final Logger LOGGER = LogManager.getLogger(ReportFileCollect.class);

	@Value("${spring.datasource.driver-class-name}")
	private String driver_class_name;

	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
    @Bean
    public DataSource dataSource(){
    	
	    LOGGER.info("dataSource() started!");
		
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(driver_class_name);
	    dataSource.setUrl(url);
	    dataSource.setUsername(username);
	    dataSource.setPassword(password);
	   
	    return dataSource;
   }
}
