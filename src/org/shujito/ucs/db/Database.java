package org.shujito.ucs.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public final class Database
{
	public static final String TAG = Database.class.getSimpleName();
	private static final Connection sConnection;
	//private static final List<String> sTransactionStack = new ArrayList<>();
	static
	{
		File file = new File("ucs.db3");
		boolean fileExists = file.exists();
		try
		{
			sConnection = DriverManager.getConnection("jdbc:sqlite:ucs.db3");
			//sConnection = DriverManager.getConnection("jdbc:sqlite::memory:");
			//sConnection.setAutoCommit(false);
			sConnection.setAutoCommit(true);
			try (Statement smt = sConnection.createStatement())
			{
				smt.executeUpdate("create table if not exists users ("
					+ "uuid text not null on conflict fail default (lower(hex(randomblob(16)))),"
					+ "created_at integer not null on conflict ignore default (cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer)),"
					+ "updated_at integer not null on conflict ignore default (cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer)),"
					+ "deleted_at integer,"
					+ "username text not null on conflict fail unique on conflict fail,"
					+ "display_name text not null on conflict fail,"
					+ "email text not null on conflict fail unique on conflict fail,"
					+ "primary key (uuid) on conflict replace"
					+ ")");
				smt.executeUpdate("create table if not exists user_passwords ("
					+ "user_uuid text not null on conflict fail unique on conflict replace,"
					+ "password blob not null on conflict fail,"
					+ "salt blob not null on conflict fail,"
					+ "foreign key (user_uuid) references users(uuid),"
					+ "primary key (user_uuid) on conflict replace"
					+ ")");
				smt.executeUpdate("create table if not exists sessions ("
					+ "user_uuid text not null on conflict fail,"
					+ "access_token blob not null on conflict fail default (randomblob(32)),"
					+ "expires_at integer not null on conflict ignore default (cast(((julianday('now','+10 minutes') - julianday('1970-01-01')) * 86400000) as integer)),"
					+ "user_agent text not null on conflict fail,"
					+ "foreign key (user_uuid) references users(uuid)"
					+ ")");
				smt.executeUpdate("create table if not exists groups ("
					+ "id integer primary key autoincrement,"
					+ "name text not null on conflict fail"
					+ ")");
				smt.executeUpdate("create table if not exists songs ("
					+ "id integer not null on conflict fail,"
					+ "ucs text not null on conflict fail,"
					+ "bpm real not null on conflict fail,"
					+ "delay real not null on conflict fail,"
					+ "artist text not null on conflict fail,"
					+ "name text not null on conflict fail,"
					+ "primary key (id) on conflict fail"
					+ ")");
				smt.executeUpdate("create table if not exists song_groups ("
					+ "group_id integer not null on conflict fail,"
					+ "song_id integer not null on conflict fail,"
					+ "foreign key (group_id) references groups(id),"
					+ "foreign key (song_id) references songs(id)"
					+ ")");
				smt.executeUpdate("create table if not exists ucs ("
					+ "uuid text not null on conflict fail default (lower(hex(randomblob(16)))),"
					+ "created_at integer not null on conflict ignore default (cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer)),"
					+ "updated_at integer not null on conflict ignore default (cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer)),"
					+ "deleted_at integer,"
					+ "song_id integer not null on conflict fail,"
					+ "user_uuid text not null on conflict fail,"
					+ "chart_data blob not null on conflict fail,"
					+ "description text not null on conflict fail,"
					+ "difficulty integer not null on conflict fail,"
					+ "foreign key (song_id) references songs(id),"
					+ "foreign key (user_uuid) references users(uuid),"
					+ "primary key (uuid) on conflict replace"
					+ ")");
				if (!fileExists)
				{
					smt.executeUpdate("insert into songs(id,ucs,bpm,delay,artist,name) values"
						+ "(516, \"CS002\", 130.5, 1920, \"BanYa\", \"Final Audition\"),"
						+ "(517, \"CS001\", 195, 3805, \"BanYa\", \"EXtravaganza\"),"
						+ "(769, \"CS011\", 130, 6200, \"BanYa\", \"Final Audition 2\"),"
						+ "(770, \"CS010\", 136, 1195.5, \"BanYa\", \"Naissance\"),"
						+ "(771, \"CS009\", 150, 4325, \"BanYa\", \"Turkey March\"),"
						+ "(774, \"CS008\", 120, 5980, \"BanYa\", \"A Nightmare\"),"
						+ "(775, \"CS007\", 105, 4692.5, \"BanYa\", \"Close Your Eye\"),"
						+ "(776, \"CS006\", 99, 2340, \"BanYa\", \"Free Style\"),"
						+ "(777, \"CS005\", 80, 515, \"BanYa\", \"Midnight Blue\"),"
						+ "(784, \"CS004\", 229.5, 1117.5, \"BanYa\", \"She Likes Pizza\"),"
						+ "(785, \"CS003\", 135, 60, \"BanYa\", \"Pumping Up\"),"
						+ "(1025, \"CS016\", 148, 1725, \"BanYa\", \"Oh! Rosa\"),"
						+ "(1027, \"CS014\", 92, 105, \"BanYa\", \"Betrayer\"),"
						+ "(1028, \"CS013\", 136, 35, \"BanYa\", \"Solitary\"),"
						+ "(1029, \"CS012\", 190, 1755, \"BanYa\", \"Mr. Larpus\"),"
						+ "(1040, \"CS015\", 104, 115, \"BanYa\", \"First Love\"),"
						+ "(1281, \"CS021\", 106, 2275, \"BanYa\", \"Pump Jump\"),"
						+ "(1282, \"CS020\", 106, 627, \"BanYa\", \"N\"),"
						+ "(1283, \"CS019\", 142, 160, \"BanYa\", \"Rolling Christmas\"),"
						+ "(1284, \"CS018\", 130, 5618, \"BanYa\", \"All I Want For X-mas\"),"
						+ "(1285, \"CS017\", 162, 187.5, \"BanYa\", \"Beethoven Virus\"),"
						+ "(2321, \"CS048\", 200, 127.5, \"BanYa\", \"CHICKEN WING\"),"
						+ "(2338, \"CS047\", 189, 182.5, \"BanYa\", \"Final Audition Ep. 1\"),"
						+ "(1793, \"CS041\", 145, 45, \"BanYa\", \"Dr. M\"),"
						+ "(1794, \"CS040\", 150, 815, \"BanYa\", \"Emperor\"),"
						+ "(1795, \"CS039\", 96, 940, \"BanYa\", \"Get Your Groove On\"),"
						+ "(1796, \"CS038\", 140, 25, \"BanYa\", \"Love is a Danger Zone\"),"
						+ "(1797, \"CS037\", 136, 790, \"BanYa\", \"Maria\"),"
						+ "(1798, \"CS036\", 124, 840, \"BanYa\", \"Mission Possible\"),"
						+ "(1799, \"CS035\", 118, 95, \"BanYa\", \"My way\"),"
						+ "(1800, \"CS034\", 92, 55, \"BanYa\", \"Point Break\"),"
						+ "(1801, \"CS033\", 124, 100, \"BanYa\", \"Street show down\"),"
						+ "(1808, \"CS032\", 110.8, 895, \"BanYa\", \"Top City\"),"
						+ "(1809, \"CS031\", 168, 1445, \"BanYa\", \"Winter\"),"
						+ "(1810, \"CS030\", 210, 85, \"BanYa\", \"Will o' The Wisp\"),"
						+ "(1811, \"CS029\", 140, 1140, \"BanYa\", \"Till the end of time\"),"
						+ "(1812, \"CS028\", 148, 645, \"BanYa\", \"Oy oy oy\"),"
						+ "(1813, \"CS027\", 118, 707.5, \"BanYa\", \"We will meet again\"),"
						+ "(1814, \"CS026\", 111, 1115, \"BanYa\", \"Miss S' story\"),"
						+ "(1815, \"CS025\", 120, 2140, \"BanYa\", \"Set me up\"),"
						+ "(1816, \"CS024\", 122, 792, \"BanYa\", \"Dance with me\"),"
						+ "(1845, \"CS023\", 184, 115, \"BanYa\", \"Vook\"),"
						+ "(1846, \"CS022\", 180, 65, \"BanYa\", \"Csikos Post\"),"
						+ "(2050, \"CS046\", 160, 139, \"BanYa\", \"Bee\"),"
						+ "(2055, \"CS045\", 150, 75, \"BanYa\", \"D Gang\"),"
						+ "(2065, \"CS044\", 140, 90, \"BanYa\", \"Hello\"),"
						+ "(2080, \"CS043\", 186, 62.5, \"BanYa\", \"Beat of The War\"),"
						+ "(2086, \"CS042\", 100, 40, \"BanYa\", \"Come to Me\"),"
						+ "(2561, \"CS055\", 130.5, -67.5, \"BanYa\", \"Final Audition 3\"),"
						+ "(2562, \"CS054\", 129, 85, \"BanYa\", \"Naissance 2\"),"
						+ "(2563, \"CS053\", 186, 222.5, \"BanYa\", \"Monkey Fingers\"),"
						+ "(2564, \"CS052\", 158, 90, \"BanYa\", \"Blazing\"),"
						+ "(2565, \"CS051\", 170, 172.5, \"BanYa\", \"Pump Me Amadeus\"),"
						+ "(2566, \"CS050\", 162, 107.5, \"BanYa\", \"X Treme\"),"
						+ "(2567, \"CS049\", 180, 77.5, \"BanYa\", \"Get Up\"),"
						+ "(2838, \"CS059\", 140, 82.5, \"BanYa\", \"J Bong\"),"
						+ "(2839, \"CS058\", 145, 82.5, \"BanYa\", \"Hi Bi\"),"
						+ "(2840, \"CS057\", 136, -250, \"BanYa\", \"Solitary 2\"),"
						+ "(2841, \"CS056\", 160, 90, \"BanYa\", \"Canon D\"),"
						+ "(3073, \"CS065\", 190, 52.5, \"BanYa\", \"Beat of The War 2\"),"
						+ "(3074, \"CS064\", 180, 40, \"BanYa\", \"Moonlight\"),"
						+ "(3075, \"CS063\", 195, 82.5, \"BanYa\", \"Witch Doctor\"),"
						+ "(3076, \"CS062\", 162, 800, \"BanYa\", \"Love is a Danger Zone pt. 2\"),"
						+ "(3077, \"CS061\", 136, 52.5, \"BanYa\", \"Phantom\"),"
						+ "(3078, \"CS060\", 145, 112.5, \"BanYa\", \"Papa Gonzales\"),"
						+ "(793604, \"CS066\", 162, 795, \"BanYa\", \"Love is a Danger Zone pt.2 (Another)\"),"
						+ "(3329, \"CS076\", 122, 85, \"YAHPP\", \"Witch Doctor #1\"),"
						+ "(3330, \"CS075\", 135, 0, \"YAHPP\", \"Arch of Darkness\"),"
						+ "(3331, \"CS074\", 210, 120, \"YAHPP\", \"Chimera\"),"
						+ "(3348, \"CS073\", 96, 130, \"Banya Production\", \"2006. LOVE SONG\"),"
						+ "(3349, \"CS072\", 120, 70, \"Banya Production\", \"Do U Know That-Old School\"),"
						+ "(3350, \"CS071\", 104, 48, \"Banya Production\", \"Gun Rock\"),"
						+ "(3351, \"CS070\", 168, 55, \"Banya Production\", \"Bullfighter's Song\"),"
						+ "(3352, \"CS069\", 92, 80, \"Banya Production\", \"Ugly Dee\"),"
						+ "(3368, \"CS068\", 170, 67, \"YAHPP\", \"Final Audition Ep. 2-1\"),"
						+ "(3376, \"CS067\", 200, 72.5, \"YAHPP\", \"Final Audition Ep. 2-2\"),"
						+ "(3585, \"CS086\", 136, 100, \"YAHPP\", \"Solitary 1.5\"),"
						+ "(3586, \"CS085\", 124, 45, \"Banya Production\", \"Beat the ghost\"),"
						+ "(3587, \"CS084\", 160, 73.5, \"Banya Production\", \"Caprice of Otada\"),"
						+ "(3588, \"CS083\", 130, 60, \"Banya Production\", \"Money\"),"
						+ "(3589, \"CS082\", 180, 74, \"Banya Production\", \"Monkey Fingers 2\"),"
						+ "(3602, \"CS081\", 162, 115, \"YAHPP\", \"Faster Z\"),"
						+ "(3603, \"CS080\", 153, 67.5, \"YAHPP\", \"Pumptris Quattro\"),"
						+ "(57659, \"CS087\", 152, 85, \"YAHPP\", \"Pumptris 8Bit ver.\"),"
						+ "(3619, \"CS079\", 120, 70, \"Banya Production\", \"Guitar Man\"),"
						+ "(3620, \"CS078\", 150, 67.5, \"Banya Production\", \"Higgledy Piggledy\"),"
						+ "(3621, \"CS077\", 120, 60, \"Banya Production\", \"Jam O Beat\"),"
						+ "(3841, \"CS096\", 170, 105, \"YAHPP\", \"Blaze Emotion\"),"
						+ "(3842, \"CS095\", 185, 167.5, \"YAHPP\", \"CannonX.1\"),"
						+ "(3843, \"CS094\", 128, 145, \"YAHPP\", \"Chopsticks Challenge\"),"
						+ "(3874, \"CS093\", 134, 80, \"Banya Production\", \"The People didn't know\"),"
						+ "(3875, \"CS092\", 180, 82.5, \"Banya Production\", \"DJ Otada\"),"
						+ "(3876, \"CS091\", 156, 110, \"Banya Production\", \"K.O.A : Alice In Wonderworld\"),"
						+ "(3877, \"CS090\", 136, 84.5, \"Banya Production\", \"My Dreams\"),"
						+ "(3878, \"CS089\", 104, 67, \"Banya Production\", \"Toccata\"),"
						+ "(3881, \"CS088\", 170, 110, \"YAHPP\", \"Final Audition EP. 2-X\"),"
						+ "(4097, \"CS114\", 190, -1142.5, \"YAHPP\", \"XTREE\"),"
						+ "(4098, \"CS113\", 158, -1320, \"YAHPP\", \"Sorceress Eliese\"),"
						+ "(4099, \"CS112\", 92, -510, \"msgoon\", \"Betrayer -act.2-\"),"
						+ "(4104, \"CS111\", 127, 77.5, \"MAX\", \"U Got 2 Know\"),"
						+ "(4115, \"CS110\", 150, 80, \"SHK\", \"Destination\"),"
						+ "(4119, \"CS109\", 200, 220, \"Doin\", \"Vacuum\"),"
						+ "(4129, \"CS108\", 96, 72.5, \"Banya Production\", \"Do It Reggae Style\"),"
						+ "(4130, \"CS107\", 152, 25, \"Banya Production\", \"Xenesis\"),"
						+ "(4131, \"CS106\", 160, 60, \"Banya Production\", \"Arirang\"),"
						+ "(4132, \"CS105\", 132, 70, \"Banya Production\", \"Tek -Club Copenhagen-\"),"
						+ "(4133, \"CS104\", 170, 92.5, \"Banya Production\", \"Hello William\"),"
						+ "(4134, \"CS103\", 130, 95, \"Banya Production\", \"Turkey March -Minimal Tunes-\"),"
						+ "(4135, \"CS102\", 164, 105, \"Banya Production\", \"Get Up (and go)\"),"
						+ "(4136, \"CS101\", 140, 70, \"Banya Production\", \"Phantom -Intermezzo-\"),"
						+ "(4137, \"CS100\", 128, 125, \"Banya Production\", \"Mission Possible -Blow Back-\"),"
						+ "(4144, \"CS099\", 116, -460, \"Banya Production\", \"Pumping Jumping\"),"
						+ "(4244, \"CS098\", 145, 963, \"Doin\", \"Tepris\"),"
						+ "(4245, \"CS097\", 185, 75, \"Doin\", \"Napalm\"),"
						+ "(4353, \"CS127\", 203, 65, \"Doin\", \"Cleaner\"),"
						+ "(4354, \"CS126\", 180.4, 365, \"Doin\", \"Interference\"),"
						+ "(4355, \"CS125\", 135, 55, \"SHK\", \"Reality\"),"
						+ "(4356, \"CS124\", 170, 48, \"SHK\", \"Take Out\"),"
						+ "(4357, \"CS123\", 128, 977, \"MAX & Rorychesell (SID-SOUND)\", \"Butterfly\"),"
						+ "(4358, \"CS122\", 155, 65, \"MAX\", \"Overblow\"),"
						+ "(4359, \"CS121\", 128, 55, \"MAX\", \"We Got 2 Know\"),"
						+ "(4387, \"CS120\", 145, 80, \"Banya Production\", \"Hungarian Dance V\"),"
						+ "(4388, \"CS119\", 130, 90, \"Banya Production\", \"The Devil\"),"
						+ "(4390, \"CS118\", 135, 50, \"SHK\", \"Native\"),"
						+ "(4433, \"CS128\", 140, 45, \"A.V.\", \"Night Duty\"),"
						+ "(4434, \"CS129\", 160.81, 90, \"V.A.\", \"Pavane\"),"
						+ "(4435, \"CS117\", 200, 80, \"Doin\", \"Pine Nut\"),"
						+ "(4436, \"CS116\", 170, 112, \"Doin\", \"ASDF\"),"
						+ "(4438, \"CS115\", 155, -738, \"Doin\", \"Says\"),"
						+ "(4448, \"CS130\", 140, -650, \"MAX & Seorryang (SID-SOUND)\", \"Jonathan's Dream\"),"
						+ "(4609, \"CS142\", 196, 55, \"Affinity\", \"Monolith\"),"
						+ "(4616, \"CS141\", 112, -515, \"Celldweller\", \"Switchback\"),"
						+ "(4617, \"CS140\", 133, 55, \"Coconut\", \"Ladybug\"),"
						+ "(4626, \"CS139\", 190, 95, \"Diclonius Kid\", \"Hardkore of the North\"),"
						+ "(4632, \"CS138\", 128, 80, \"Future Funk Squad\", \"Rippin' It Up\"),"
						+ "(4633, \"CS137\", 150, 90, \"Hi-G\", \"Tribe Attacker\"),"
						+ "(4641, \"CS136\", 180, 42.5, \"Inspector K\", \"Virtual Emotion\"),"
						+ "(4646, \"CS135\", 185, 695, \"KURi-ZiLL\", \"Heel and Toe\"),"
						+ "(4656, \"CS133\", 125, 47.5, \"Sanxion7\", \"Rainspark\"),"
						+ "(4663, \"CS132\", 142, 75, \"Stian K\", \"Be Alive (Raaban Inc. Mix)\"),"
						+ "(4673, \"CS131\", 155, 45, \"Zircon\", \"Star Command\"),"
						+ "(4869, \"CS152\", 162, 807, \"DM Ashura\", \"Elise\"),"
						+ "(4870, \"CS151\", 220, 52, \"BanYa & DM Ashura\", \"Ignis Fatuus (DM Ashura Mix)\"),"
						+ "(4871, \"CS150\", 155, 212, \"BanYa & Cranky\", \"Love Is A Danger Zone (Cranky Mix)\"),"
						+ "(4872, \"CS149\", 175, 27, \"BanYa & SynthWulf\", \"Hypnosis (SynthWulf Mix)\"),"
						+ "(4873, \"CS148\", 200, 32, \"Doin\", \"FFF\"),"
						+ "(4880, \"CS147\", 145, 36, \"SHK\", \"Unique\"),"
						+ "(4881, \"CS146\", 183, 47, \"MAX\", \"Accident\"),"
						+ "(4882, \"CS145\", 180, -138, \"MAX\", \"D\"),"
						+ "(4883, \"CS144\", 128, 19.5, \"MAX\", \"U Got Me Rocking\")");
				}
			}
			//sConnection.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
			throw new RuntimeException(e);
		}
	}
	
	public static final Statement createStatement() throws Exception
	{
		return sConnection.createStatement();
	}
	
	public static final PreparedStatement prepareStatement(String sql) throws Exception
	{
		return sConnection.prepareStatement(sql);
	}
	
	private Database()
	{
		throw new RuntimeException("do not!");
	}
}
