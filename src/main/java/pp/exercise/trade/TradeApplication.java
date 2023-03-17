package pp.exercise.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import pp.exercise.trade.model.Procedures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootApplication
public class TradeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeApplication.class, args);
		try {
			InputStream inputStream = new FileInputStream("src/main/resources/procedures.yaml");

			Yaml yaml = new Yaml(new Constructor(Procedures.class));
			Procedures procedures = yaml.load(inputStream);

			System.out.println(procedures);
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

}
