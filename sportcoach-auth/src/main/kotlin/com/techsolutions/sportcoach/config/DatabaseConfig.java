package com.techsolutions.sportcoach.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DatabaseConfig {

	@Autowired
	private Environment environment;
	
	/*@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2) // .H2 or .DERBY, etc.
				.addScript("user.sql").addScript("user-role.sql").build();
		return db;
	}*/

	/*@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(environment.getProperty("com.mysql.cj.jdbc.Driver"));
		dataSource.setUrl(environment.getProperty("jdbc:mysql://localhost:3306/identity?serverTimezone=UTC"));
		dataSource.setUsername(environment.getProperty("root"));
		dataSource.setPassword(environment.getProperty("root"));

		return dataSource;
	}*/

	/*@Bean
	public JdbcTemplate getJdbcTemplate() throws ClassNotFoundException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}*/

}
