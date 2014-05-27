import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.epam.testapp.database.DBUtil;
import com.epam.testapp.database.JdbcNewsDAO;
import com.epam.testapp.exception.NoConnectionAvailableException;
import com.epam.testapp.model.News;

public class FillingDBwithData {

	public static void main(String[] args) throws SQLException,
			NoConnectionAvailableException, ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1522:newsdb", "root", "pass");
		CallableStatement call = conn
				.prepareCall("{CALL insert_news(?,?,?,?,?)}");
		News news = new News();
		news.setTitle("Exercise is good for mental health, too");
		news.setBrief("Q: I recently went to the doctor and he prescribed an anti-depressant. I have used it for the last two months and do feel more in control, but I have gained a lot of weight. I would like to get off and talked to the doctor about my alternatives and he mentioned exercise. How does that work? What is the best exercise?");
		news.setContent("A: Several studies have been done, including a couple by Duke University. Each one made a strong correlation between exercise and strong mental health. For the study, they simply used walking three days a week and determined that the changes from walking briskly were as effective for mild depression as any of the drugs on the market."
				+ "Exercise affects serotonin in much the same way that several anti-depressants do. One difference is the walking may help you to lose weight instead of gaining weight. The exercise has to be brisk and challenging and doesn’t matter if it is cardio or resistance training. The problem with this approach is it takes more motivation to begin and often a partner is a necessity to get the jumpstart to get going."
				+ "Getting started is the most difficult part. Getting outside and walking briskly, especially after this winter, is a great way to feel better. If you would like a more challenging routine, then training in a gym can be even more effective in making changes to your physique. Getting back in shape is just a side reward along with your improved mental health. This is not a recommendation to stop using what your doctor has prescribed without a medical consultation. It is an opportunity to return to both physical and mental health. God bless and keep training.");
		news.setDate(formatter.parse("03/17/2014"));
		call.setString(1, news.getTitle().trim());
		call.setString(2, news.getBrief().trim());
		call.setString(3, news.getContent().trim());
		call.setDate(4, new java.sql.Date(news.getDate().getTime()));
		call.registerOutParameter(5, java.sql.Types.NUMERIC);
		call.execute();

		news.setTitle("New to the ‘Discworld’ series? Start with ‘The Truth’");
		news.setBrief("“The Truth” (Discworld Series), by Terry Pratchett. Copyright 2001, HarperCollins. $7.99 mass-market paperback.");
		news.setContent("With more than 30 books, the “Discworld” series by Terry Pratchett is well-known in the United Kingdom and is gaining steam here in the United States.“Discworld” is a fantasy/satire series, appealing both to traditional fantasy fans as well as those who think unicorns are, as one of Pratchett’s characters thinks, just a horse that comes to a point. It’s what you get when you take a magic-filled flat world sitting on the back of four giant elephants, which in turn stand on the back of a goliath space-turtle, and then populate it with blunt realists. Although the series is slightly self-referential, there is no need to read them in order, though those who become hooked will benefit from re-readings later on. The 25th book in the series, “The Truth,” generally is agreed to be a good place to start. Ankh-Morpork is a city that lives on gossip, and William de Worde writes it all down, sending it in a monthly letter to the upper-crust of Ankh-Morpork’s friends and rivals. It’s an easy life, and when rumor says that the dwarves can turn lead into gold, he writes that down, too. But when the aforementioned lead turns out to be a printing press, Williams finds himself enlisted as the head writer for Discworld’s first newspaper. ");
		call.setString(1, news.getTitle().trim());
		call.setString(2, news.getBrief().trim());
		call.setString(3, news.getContent().trim());
		call.registerOutParameter(5, java.sql.Types.NUMERIC);
		call.execute();

		news.setTitle("Ukraine crisis: Early results show Crimea votes to join Russia");
		news.setBrief("Perevalnoye, Ukraine (CNN) -- Preliminary results in Sunday's referendum on whether Ukraine's Crimea region should join Russia or become an independent state show overwhelming support for Russia.");
		news.setContent("With 75% percent of the ballots counted, close to 96% of voters want to become part of that country, according to the Crimean Electoral Commission. An official had announced earlier that more than 80% of voters had cast ballots by the time polls closed at 8 p.m. local time (2 p.m. ET) Sunday.");
		call.setString(1, news.getTitle().trim());
		call.setString(2, news.getBrief().trim());
		call.setString(3, news.getContent().trim());
		call.registerOutParameter(5, java.sql.Types.NUMERIC);
		call.execute();

		news.setTitle("Russian squeeze");
		news.setBrief("Pro-Russian troops remain firmly in control of the Black Sea peninsula. Ukraine and the West insist the soldiers belong to Moscow, but the Kremlin vehemently denies it, saying they are Crimean \"self-defense\" forces.");
		news.setContent("Ukraine's acting Defense Minister Ihor Tenyukh said Sunday that Ukraine had reached an agreement \"with the Russian side\" that Russian forces will allow the delivery of food and basic supplies to Ukrainian military bases in Crimea until Friday. The bases have been blockaded for days. Tenyukh told a Cabinet meeting that there are now 21,500 Russian troops on Crimean soil. Russia is entitled to station 25,000 troops at its leased Sevastopol naval base -- but the question is where those troops are. Tenyukh also said Ukrainian troops and equipment are being moved into Ukraine's east and south, in line with where Russian military forces are located. Moscow has been carrying out mass military exercises not far from Ukraine's eastern border. Russia tightened its military grip on Saturday within Ukraine. About 60 Russian troops in six helicopters and three armored vehicles reportedly crossed into Ukraine's Kherson region and were in the town of Strilkove, on a strip of land just northeast of Crimea.");
		call.setString(1, news.getTitle().trim());
		call.setString(2, news.getBrief().trim());
		call.setString(3, news.getContent().trim());
		call.registerOutParameter(5, java.sql.Types.NUMERIC);
		call.execute();

		news.setTitle("What happens next in Crimea?");
		news.setBrief("If the vote goes in favor of joining Russia, as it looks like it will, Crimea's government will declare its independence and ask Moscow to let it join the Russian Federation. Russian lawmakers have said they'll vote on the question on Friday.");
		news.setContent("Christopher Hill, a former U.S. ambassador to South Korea, Iraq and Poland, described Sunday as a bad day for East-West relations. \"Putin has left our president with no choice. He needs to impose sanctions. I know Putin will come back and impose his own,\" he said. \"I think the end of this is going to be to cast Russia out into the cold. And the problem is, I don't think Putin really cares. I think this is where he wants to take Russia.\" In Simferopol and other places with Russian majorities, blue, white and red Russian flags have dominated the streets. In the coastal Crimean town of Sevastopol, concerts on the main square have been celebrating the return to the \"motherland\" this past week. \"Everybody believes the results are already rigged,\" said CNN iReporter Maia Mikhaluk from Kiev. \"People are concerned what is going to happen after the referendum,\" she said. \"People are concerned that the Russian army will use force, guns to push (the) Ukrainian army from Crimea.\" In the city of Donetsk, near the Russian border in eastern Ukraine, pro-Russian demonstrators stormed the prosecutor's office, forcing their way through a door of the building. The activists are demanding the release of pro-Moscow movement leader Pavel Gubarev, who was arrested on March 6 for leading an occupation of the regional administration office. Earlier, thousands of pro-Russian demonstrators gathered for a second day in a central Donetsk square before marching through the city. Riot police stood on guard outside the offices of Ukraine's security service and the regional administration.");
		call.setString(1, news.getTitle().trim());
		call.setString(2, news.getBrief().trim());
		call.setString(3, news.getContent().trim());
		call.registerOutParameter(5, java.sql.Types.NUMERIC);
		call.execute();

	}
}
