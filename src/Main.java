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

//        findGoldMedalsWonPerEveryOlympicYearOfEachPlayer(events);
//        findAthletesWhoWonGoldMedalIn1980AndAgeIsLessThan30Years(events);
//        findEventWiseNumberOfMedalsIn1980(events);
//        findGoldWinnerOfFootballOfEveryOlympic(events);
//        findFemaleAthleteWhoWonMaximumNumberOfGoldAllOlympics(events);
        findNameOfAthleteParticipatedInMoreThanThreeOlympics(events);


    }
    public static List<Event> readEventsData(){
        List<Event> allEvents= new ArrayList<>();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("athlete_events.csv"));
            String l=bufferedReader.readLine();
            while ((l= bufferedReader.readLine()) != null){
                if(l.startsWith("ID")){
                    continue;
                }
                String [] eachEvent= l.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)",-1);
                for (int i = 0; i < eachEvent.length; i++) {
                    if (eachEvent[i].startsWith("\"") && eachEvent[i].endsWith("\"")) {
                        eachEvent[i] = eachEvent[i].substring(1,eachEvent[i].length() - 1);
                        if(eachEvent[i].equals("NA")){
                            eachEvent[i]="0";
                        }
                    }
                    if(eachEvent[i].equals("NA")){
                        eachEvent[i]="0";
                    }
                }
                Event event= new Event();

                event.setId(eachEvent[ID]);
                event.setName(eachEvent[NAME]);
                event.setSex(eachEvent[SEX]);
                event.setAge((eachEvent[AGE]));
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
        System.out.println("QUESTION 1");
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

    public static void findAthletesWhoWonGoldMedalIn1980AndAgeIsLessThan30Years(List<Event> events){

        Set<String> athletesWhoWonGoldMedals= new HashSet<>();
        int count=0;
        for(Event event :events){
            int age = Integer.parseInt(event.getAge());
            if(event.getYear().equals("1980")&& event.getMedal().equals("Gold")&& age < 30){
                athletesWhoWonGoldMedals.add(event.getName());
            }
        }
        System.out.println("QUESTION 2");
        for(String name : athletesWhoWonGoldMedals){
            System.out.println(name);
        }

    }
    public static void findEventWiseNumberOfMedalsIn1980(List<Event>events){
        int count=0;

        Map<Map<String , String >, Integer> medalsOfEachEvent = new HashMap<>();
        for( Event event: events){
            if(event.getYear().equals("1980")){
                count++;
                Map<String, String> eventMedals= new HashMap<>();
                if(event.getMedal().equals("NA")) {
                    continue;
                }
                eventMedals.put(event.getEvent() , event.getMedal());
                medalsOfEachEvent.put(eventMedals, medalsOfEachEvent.getOrDefault(eventMedals, 0)+1);

            }

        }
        System.out.println("QUESTION 3");
        for ( Map.Entry<Map<String, String>, Integer> entry: medalsOfEachEvent.entrySet()){
            Map<String, String> medalEvent = entry.getKey();
            int medals = entry.getValue();
            for (Map.Entry<String, String> medalEventval : medalEvent.entrySet()) {
                String key = medalEventval.getKey();
                String value = medalEventval.getValue();
                System.out.print("Event: " + key + " medal: " + value);
            }
            System.out.println(" count: " + medals);
        }

    }
    public static void findGoldWinnerOfFootballOfEveryOlympic(List<Event> events){
        HashMap<String , String> goldWinnersOfFootball= new HashMap<>();

        for(Event event : events){
            if(event.getSport().equals("Football") && event.getMedal().equals("Gold")){
                goldWinnersOfFootball.put(event.getYear(), event.getTeam());
            }
        }
        System.out.println("QUESTION 4");

        for( Map.Entry<String , String> entryList : goldWinnersOfFootball.entrySet() ){
            System.out.println("In year " + entryList.getKey()+" "+ entryList.getValue() + " won FootBall Olympic gold medal" );
        }
    }

    public static void findFemaleAthleteWhoWonMaximumNumberOfGoldAllOlympics(List<Event> events){

        Map<String , Integer> femaleAtheleteGoldMedals= new HashMap<>();

        for ( Event event : events){
            if(event.getSex().equals("F") && event.getMedal().equals("Gold")){
                femaleAtheleteGoldMedals.put(event.getName(),
                        femaleAtheleteGoldMedals.getOrDefault(event.getName() , 0)+1);
            }
        }
        System.out.println("QUESTION 5");
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(femaleAtheleteGoldMedals.entrySet());
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for(Map.Entry<String , Integer> entry: entryList){
            System.out.println(entry.getKey() + " has won " + entry.getValue());
            break;
        }
    }
    public static void findNameOfAthleteParticipatedInMoreThanThreeOlympics(List<Event> events){
        Map<Map<String, String> , Integer> athletsOlympicsCount = new HashMap<>();
        for ( Event event : events){
            Map<String , String> athleteSeason= new HashMap<>();
            athleteSeason.put(event.getName(), event.getSeason());
            athletsOlympicsCount.put(athleteSeason, athletsOlympicsCount.getOrDefault(athleteSeason, 0)+1);

        }
        System.out.println("QUESTION 6");
        for ( Map.Entry<Map<String, String>, Integer> entry: athletsOlympicsCount.entrySet()){
            Map<String, String> athleteSeason = entry.getKey();
            int count = entry.getValue();
            if(count >=3){
                for (Map.Entry<String, String> athleteSeasonVal : athleteSeason.entrySet()) {
                    String key = athleteSeasonVal.getKey();
                    String value = athleteSeasonVal.getValue();
                    System.out.print( key );
                }
                System.out.println(" Appeared in " + count + " Olympics.");
            }
        }
    }
}