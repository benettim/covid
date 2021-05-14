package fbb.corona.corona.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fbb.corona.corona.Models.LocationStats;
import fbb.corona.corona.Services.CoronaVirusDataService;



@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
    	
    	List<LocationStats> allStats = coronaVirusDataService.getAllStats();
    	int totalDeaths = allStats.stream().mapToInt(stat -> stat.getDeaths()).sum()/2;
    	int newCases = allStats.stream().mapToInt(stat -> stat.getNewCases()).sum()/2;
    	int newDeaths = allStats.stream().mapToInt(stat -> stat.getNewDeaths()).sum()/2;
    	
    	//atributos soltos no thymeleaf
    	model.addAttribute("totalDeaths", totalDeaths);
        model.addAttribute("newCases", newCases);
        model.addAttribute("newDeaths", newDeaths);
    	
        //todos os atributos da model no thymeleaf
        model.addAttribute("locationStats", coronaVirusDataService.getAllStats());
        
        
        return "home";
    }
    
    @GetMapping("/{state}")
    public String estado(@PathVariable String state, Model model) 
    {
    	List<LocationStats> allStats = coronaVirusDataService.getAllStats();
    	List<LocationStats> stateStats = new ArrayList<LocationStats>();
    	
    	int totalDeaths = allStats.stream().mapToInt(stat -> stat.getDeaths()).sum()/2;
    	int newCases = allStats.stream().mapToInt(stat -> stat.getNewCases()).sum()/2;
    	int newDeaths = allStats.stream().mapToInt(stat -> stat.getNewDeaths()).sum()/2;
    	
    	int totalCases = allStats.stream().mapToInt(stat -> stat.getTotalCases()).sum()/2;
    	int recovered = allStats.stream().mapToInt(stat -> stat.getRecovered()).sum()/2;
    	
    	
    	
    	//atributos soltos no thymeleaf
    	model.addAttribute("totalDeaths", totalDeaths);
        model.addAttribute("newCases", newCases);
        model.addAttribute("newDeaths", newDeaths);
        model.addAttribute("recovered", recovered);
        int ativos = 0;
        int mortes = 0;
        int recuperados = 0;
        //todos os atributos da model no thymeleaf
        for (LocationStats item : allStats) {
        	if(item.getState().equals(state)) {
        		stateStats.add(item);
        		mortes = item.getDeaths();
        		recuperados = item.getRecovered();
        		ativos = item.getTotalCases() - item.getDeaths() - item.getRecovered();
        		
        	}
        	 
		}
        model.addAttribute("locationStats", stateStats);
        
        model.addAttribute("ativos", ativos);
		model.addAttribute("mortes", mortes);
		model.addAttribute("recuperados", recuperados);
		
        
        return "PieChart";
    }
}
