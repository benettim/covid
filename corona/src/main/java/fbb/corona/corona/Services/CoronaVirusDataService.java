package fbb.corona.corona.Services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fbb.corona.corona.Models.LocationStats;



@Service
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/wcota/covid19br/master/cases-brazil-total.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    //Scheduled permite agendar atualizações, sendo o terceiro digito equivalendo a hora do dia
    @Scheduled(cron = "* * 1 * * *")
    
    public void fetchVirusData() throws IOException, InterruptedException {
    	
    	//newStats meio desnecessário para o projeto. Serve para caso algum cliente acesse o site durante o fetch, mostre o allStats antigo ao invés do novo.
        List<LocationStats> newStats = new ArrayList<>();
        
        //codigo que acessa os dados crus no link do github. A terceira linha exige um throws IOException
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        /*
         * Biblioteca CSV da CSV Commons https://commons.apache.org/proper/commons-csv/
         * Possui a dependência pronta para o pom.xml
         * Possui exemplo de código pronto para percorrer arquivos CSV, no caso um exemplo para CSV com cabeçalho
         */
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("state"));
            locationStat.setTotalCases(Integer.parseInt(record.get("totalCases")));
            locationStat.setDeaths(Integer.parseInt(record.get("deaths")));
            locationStat.setRecovered(Integer.parseInt(record.get("recovered")));
            locationStat.setNewCases(Integer.parseInt(record.get("newCases")));
            locationStat.setNewDeaths(Integer.parseInt(record.get("newDeaths")));
            newStats.add(locationStat);
        }
        
        this.allStats = newStats;
    }

}
