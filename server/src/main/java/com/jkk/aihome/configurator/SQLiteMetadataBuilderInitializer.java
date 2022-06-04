package com.jkk.aihome.configurator;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.engine.jdbc.dialect.internal.DialectResolverSet;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

@Slf4j
public class SQLiteMetadataBuilderInitializer implements MetadataBuilderInitializer {
	@Override
	public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry serviceRegistry) {
		DialectResolver dialectResolver = serviceRegistry.getService(DialectResolver.class);

		if (!(dialectResolver instanceof DialectResolverSet)) {
			log.warn("DialectResolver '{}' is not an instance of DialectResolverSet, not registering SQLiteDialect",
					dialectResolver);
			return;
		}

		((DialectResolverSet) dialectResolver).addResolver(resolver);
	}

	static private final SQLiteDialect dialect = new SQLiteDialect();

	static private final DialectResolver resolver = info -> {
		if (info.getDatabaseName().equals("SQLite")) {
			return dialect;
		}

		return null;
	};
}
