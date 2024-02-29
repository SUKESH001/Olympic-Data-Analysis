import Models.Event;
import Models.Region;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final int ID=0;
    public static final int NAME=1;
    public static final int SEX=2;
    public static final int AGE=3;
    public static final int HEIGHT=4;
    public static final int WEIGHT=5;
    public static final int TEAM=6;
    public static final int NOC=7;
    public static final int GAMES=8;
    public static final int YEAR=9;
    public static final int SEASON=10;
    public static final int CITY=11;
    public static final int SPORT=12;
    public static final int EVENT=13;
    public static final int MEDAL=14;

    public  static final int NOC1=0;
    public static final int REGION=1;
    public static final int NOTES=2;

    public static void main(String[] args) {

        List<Event> events= readEventsData();
        List<Region> regions= readRegionsData();

        findGoldMedalsWonPerEveryOlympicYearOfEachPlayer(events);


    }
    public static List<Event> readEventsData(){
        List<Event> allEvents= new ArrayList<>();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("athlete_events.csv"));
            String l;
            while ((l = bufferedReader.readLine()) != null){
                if(l.startsWith("ID")){
                    continue;
                }
                String [] eachEvent= l.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                for (int i = 0; i < eachEvent.length; i++) {

                    if (eachEvent[i].startsWith("\"") && eachEvent[i].endsWith("\"")) {
                        eachEvent[i] = eachEvent[i].substring(1,eachEvent[i].length() - 1);
                    }
                }
                Event event= new Event();

                event.setId(eachEvent[ID]);
                event.setName(eachEvent[NAME]);
                event.setSex(eachEvent[SEX]);
                event.setAge(eachEvent[AGE]);
                event.setHeight(eachEvent[HEIGHT]);
                event.setWeight(eachEvent[WEIGHT]);
                event.setTeam(eachEvent[TEAM]);
                event.setNoc(eachEvent[NOC]);
                event.setGames(eachEvent[GAMES]);
                event.setYear(eachEvent[YEAR]);
                event.setSeason(eachEvent[SEASON]);
                event.setCity(eachEvent[CITY]);
                event.setSport(eachEvent[SPORT]);
                event.setEvent(eachEvent[EVENT]);
                event.setMedal(eachEvent[MEDAL]);

                allEvents.add(event);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allEvents;
    }
    public static List<Region> readRegionsData(){
        List<Region> allRegionsData= new ArrayList<>();
        try{
            BufferedReader bufferedReader= new BufferedReader(new FileReader("noc_regions.csv"));
            String l="";
            while((l= bufferedReader.readLine())!=null){
                if(l.startsWith("NOC")){
                    continue;
                }
                String [] eachRegion= l.split(",");

                Region region = new Region();
                region.setNoc(eachRegion[NOC1]);
                region.setRegion(eachRegion[REGION]);
                if(eachRegion.length >2){
                    region.setNotes(eachRegion[NOTES]);
                }
                allRegionsData.add(region);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allRegionsData;
    }

    public static void findGoldMedalsWonPerEveryOlympicYearOfEachPlayer(List<Event> events){

        Map<Map<String , String >, Integer> goldMedalsOfEachPlayer = new HashMap<>();
        for( Event event: events){
            Map<String, String> playerYear = new HashMap<>();
            if(event.getMedal().equals("Gold")){
                playerYear.put(event.getYear() , event.getName());

                goldMedalsOfEachPlayer.put(playerYear, goldMedalsOfEachPlayer.getOrDefault(playerYear, 0)+1);
            }
        }
        for ( Map.Entry<Map<String, String>, Integer> entry: goldMedalsOfEachPlayer.entrySet()){
            Map<String, String> yearPlayed = entry.getKey();
            int goldMedals = entry.getValue();
            for (Map.Entry<String, String> yearAndPlayer : yearPlayed.entrySet()) {
                String key = yearAndPlayer.getKey();
                String value = yearAndPlayer.getValue();
                System.out.print("In year " + key + " "+ value);
            }
            System.out.println(" won " + goldMedals+ " gold medal");

        }
    }

    public static void findAthletesWhoWonGoldMedalIn1980AndAgeIsLessThan30Years(){


    }

}