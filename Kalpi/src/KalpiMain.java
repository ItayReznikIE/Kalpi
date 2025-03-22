import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;



public class KalpiMain { 
	static Scanner sc = new Scanner (System.in);
	static Queue votersQueueToSecurityCheck = new Queue();
	static Queue votersQueueToVote = new Queue();;
	static VotesList votesList;
	static double endTime;
	static Vector<SecurityGuard> SecurityGuards;
	static VotingSystem v1;
	static VotingSystem v2;
	static VotesCounter rivka;
	static Vector<Thread> Threads = new Vector<Thread>();



	public static void main(String[] args) {
		receiveEndDayTime();
		start();
	}

	public static void receiveEndDayTime()//receive end gay time from user
	{
		System.out.println("Please enter closing kalfi time");
		endTime = sc.nextInt();
		if(endTime>24)
		{
			endTime = 24;
			System.out.println("your input bigger than 24, so it will be 24");
		}
		if(endTime<0)
		{
			endTime = 0;
			System.out.println("your input smaller than 0, so it will be 0");
		}
	}

	public static void start()//start the day
	{
		initLists();
		initSecurityGuards();
		initVotingSystems();
		initVotesCounter();
		initVoters();
		startThreads();
	}


	public static void initLists()//init lists
	{
		votersQueueToSecurityCheck = new Queue(); 
		votersQueueToVote = new Queue();
		votesList = new VotesList();
	}

	public static void initSecurityGuards()//recive input from user and create secutiry guards accordingly
	{
		System.out.println("Please enter number of security guards between 1-4");
		String numOfGuardsStr = sc.next();
		while(numOfGuardsStr == null || numOfGuardsStr.length() != 1 || (numOfGuardsStr.charAt(0) > '4' || numOfGuardsStr.charAt(0) < '1'))
		{
			System.out.println("Invalid input, please enter new input");
			numOfGuardsStr = sc.next();
		}
		int numOfGuards = Integer.parseInt(numOfGuardsStr);
		createSecurityGuardsThreads(numOfGuards);
	}
	

	public static void createSecurityGuardsThreads(int numOfGuards)//creat the security guards
	{
		Vector<String> idList = readIdList(	"/Users/itayreznik/Desktop/id list (assignment 4).txt"+ "");
		SecurityGuards = new Vector<SecurityGuard>();

		for(int i=0; i<numOfGuards; i++)
		{
			SecurityGuard s = new SecurityGuard(idList,votersQueueToVote,votersQueueToSecurityCheck);
			SecurityGuards.add(s);
			Thread t = new Thread(s);
			Threads.add(t);
		}
	}

	public static void initVotingSystems()//init lists
	{
		v1 = new VotingSystem(votersQueueToVote,votesList);
		v2 = new VotingSystem(votersQueueToVote,votesList);
		Thread votingSystem1Thread = new Thread(v1);
		Thread votingSystem2Thread = new Thread(v2);
		Threads.add(votingSystem1Thread);
		Threads.add(votingSystem2Thread);
		
	}

	public static void initVotesCounter()//init lists
	{
		rivka = new VotesCounter(votesList,endTime,SecurityGuards,v1,v2,votersQueueToSecurityCheck,votersQueueToVote);
		Thread rivkaThread = new Thread(rivka);
		Threads.add(rivkaThread);
		v1.updateVotesCounter(rivka);
		v2.updateVotesCounter(rivka);
		v1.updateSecurityGuards(SecurityGuards);
		v2.updateSecurityGuards(SecurityGuards);

	}

	


	public static void initVoters() //init voters
	{
		Vector<Voter> voters = new Vector<Voter>();
		try
		{
			voters = ReadVotersDataFile("/Users/itayreznik/Desktop/voters data (assignment 4).txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for(int i =0; i< voters.size(); i++)
		{
			Thread t = new Thread(voters.elementAt(i));
			Threads.add(t);
		}
	}


	public static void 	startThreads()
	{
		for(int i =0; i<Threads.size(); i++)
		{
			Threads.elementAt(i).start();
		}

	}


	private static Vector<Voter> ReadVotersDataFile(String votersData) throws FileNotFoundException{ //converts the votes text files to data in the fields
		Vector<Voter> voters = new Vector<>();
		Path pathToFile = Paths.get(votersData);
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] Data = line.split("\t");
				Voter v = createVoter(Data);
				voters.add(v);
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return voters;
	}

	public static Voter createVoter(String[] Data) //creates voter from data in file
	{
		String name = Data[0];
		String lastName = Data[1];
		String id = Data[2];
		int age = Integer.parseInt(Data[3]);
		String mayorCandidateSelection = Data[4];
		String listSelection = Data[5];
		int time = Integer.parseInt(Data[6]);
		return new Voter(name,lastName,id,age,mayorCandidateSelection,listSelection, time,endTime,votersQueueToSecurityCheck);
	}

	private static Vector<String> readIdList(String idList)//reads the voters data file and updates list accordingly
	{
		Vector<String> votersIdList = new Vector<String>();
		Path pathToFile = Paths.get(idList);
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			String line = br.readLine(); //read the first line in the file
			line = br.readLine();
			while (line !=null)
			{
				votersIdList.add(line);
				line = br.readLine(); //next line
			}
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		return votersIdList;
	}




}