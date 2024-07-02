# Generoi openapi-kuvauksen
gen_openapi:
	@mvn clean compile -P generate-openapi \
	  && cp target/openapi/eperusteet-ai.spec.json generated
		
