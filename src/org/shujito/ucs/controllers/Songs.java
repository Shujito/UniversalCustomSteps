package org.shujito.ucs.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.shujito.ucs.ApiException;
import org.shujito.ucs.models.Group;
import org.shujito.ucs.models.Song;

/**
 * <p>UCS Enabled songs.</p>
 * <p>Leaving the lists hardcoded there, since it doesn't need to be in the database.</p>
 * @author shujito
 */
@Path("/songs")
@Produces(MediaType.APPLICATION_JSON)
public class Songs
{
	public static final String TAG = Songs.class.getSimpleName();
	public static final List<Song> songs;
	public static final Map<Integer, Song> songIndexes;
	public static final List<Group> songGroups;
	static
	{
		songs = new ArrayList<>();
		songIndexes = new HashMap<>();
		songGroups = new ArrayList<>();
		/* 1st - 3rd */
		songs.add(new Song(0x204, "CS002", 130.5, 1920, "BanYa", "Final Audition"));
		songs.add(new Song(0x205, "CS001", 195, 3805, "BanYa", "EXtravaganza"));
		songs.add(new Song(0x301, "CS011", 130, 6200, "BanYa", "Final Audition 2"));
		songs.add(new Song(0x302, "CS010", 136, 1195.5, "BanYa", "Naissance"));
		songs.add(new Song(0x303, "CS009", 150, 4325, "BanYa", "Turkey March"));
		songs.add(new Song(0x306, "CS008", 120, 5980, "BanYa", "A Nightmare"));
		songs.add(new Song(0x307, "CS007", 105, 4692.5, "BanYa", "Close Your Eye"));
		songs.add(new Song(0x308, "CS006", 99, 2340, "BanYa", "Free Style"));
		songs.add(new Song(0x309, "CS005", 80, 515, "BanYa", "Midnight Blue"));
		songs.add(new Song(0x310, "CS004", 229.5, 1117.5, "BanYa", "She Likes Pizza"));
		songs.add(new Song(0x311, "CS003", 135, 60, "BanYa", "Pumping Up"));
		/* SE - Extra */
		songs.add(new Song(0x401, "CS016", 148, 1725, "BanYa", "Oh! Rosa"));
		songs.add(new Song(0x403, "CS014", 92, 105, "BanYa", "Betrayer"));
		songs.add(new Song(0x404, "CS013", 136, 35, "BanYa", "Solitary"));
		songs.add(new Song(0x405, "CS012", 190, 1755, "BanYa", "Mr. Larpus"));
		songs.add(new Song(0x410, "CS015", 104, 115, "BanYa", "First Love"));
		songs.add(new Song(0x501, "CS021", 106, 2275, "BanYa", "Pump Jump"));
		songs.add(new Song(0x502, "CS020", 106, 627, "BanYa", "N"));
		songs.add(new Song(0x503, "CS019", 142, 160, "BanYa", "Rolling Christmas"));
		songs.add(new Song(0x504, "CS018", 130, 5618, "BanYa", "All I Want For X-mas"));
		songs.add(new Song(0x505, "CS017", 162, 187.5, "BanYa", "Beethoven Virus"));
		songs.add(new Song(0x911, "CS048", 200, 127.5, "BanYa", "CHICKEN WING"));
		songs.add(new Song(0x922, "CS047", 189, 182.5, "BanYa", "Final Audition Ep. 1"));
		/* Rebirth - Prex3 */
		songs.add(new Song(0x701, "CS041", 145, 45, "BanYa", "Dr. M"));
		songs.add(new Song(0x702, "CS040", 150, 815, "BanYa", "Emperor"));
		songs.add(new Song(0x703, "CS039", 96, 940, "BanYa", "Get Your Groove On"));
		songs.add(new Song(0x704, "CS038", 140, 25, "BanYa", "Love is a Danger Zone"));
		songs.add(new Song(0x705, "CS037", 136, 790, "BanYa", "Maria"));
		songs.add(new Song(0x706, "CS036", 124, 840, "BanYa", "Mission Possible"));
		songs.add(new Song(0x707, "CS035", 118, 95, "BanYa", "My way"));
		songs.add(new Song(0x708, "CS034", 92, 55, "BanYa", "Point Break"));
		songs.add(new Song(0x709, "CS033", 124, 100, "BanYa", "Street show down"));
		songs.add(new Song(0x710, "CS032", 110.8, 895, "BanYa", "Top City"));
		songs.add(new Song(0x711, "CS031", 168, 1445, "BanYa", "Winter"));
		songs.add(new Song(0x712, "CS030", 210, 85, "BanYa", "Will o' The Wisp"));
		songs.add(new Song(0x713, "CS029", 140, 1140, "BanYa", "Till the end of time"));
		songs.add(new Song(0x714, "CS028", 148, 645, "BanYa", "Oy oy oy"));
		songs.add(new Song(0x715, "CS027", 118, 707.5, "BanYa", "We will meet again"));
		songs.add(new Song(0x716, "CS026", 111, 1115, "BanYa", "Miss S' story"));
		songs.add(new Song(0x717, "CS025", 120, 2140, "BanYa", "Set me up"));
		songs.add(new Song(0x718, "CS024", 122, 792, "BanYa", "Dance with me"));
		songs.add(new Song(0x735, "CS023", 184, 115, "BanYa", "Vook"));
		songs.add(new Song(0x736, "CS022", 180, 65, "BanYa", "Csikos Post"));
		songs.add(new Song(0x802, "CS046", 160, 139, "BanYa", "Bee"));
		songs.add(new Song(0x807, "CS045", 150, 75, "BanYa", "D Gang"));
		songs.add(new Song(0x811, "CS044", 140, 90, "BanYa", "Hello"));
		songs.add(new Song(0x820, "CS043", 186, 62.5, "BanYa", "Beat of The War"));
		songs.add(new Song(0x826, "CS042", 100, 40, "BanYa", "Come to Me"));
		/* Exceed - Zero */
		songs.add(new Song(0xA01, "CS055", 130.5, -67.5, "BanYa", "Final Audition 3"));
		songs.add(new Song(0xA02, "CS054", 129, 85, "BanYa", "Naissance 2"));
		songs.add(new Song(0xA03, "CS053", 186, 222.5, "BanYa", "Monkey Fingers"));
		songs.add(new Song(0xA04, "CS052", 158, 90, "BanYa", "Blazing"));
		songs.add(new Song(0xA05, "CS051", 170, 172.5, "BanYa", "Pump Me Amadeus"));
		songs.add(new Song(0xA06, "CS050", 162, 107.5, "BanYa", "X Treme"));
		songs.add(new Song(0xA07, "CS049", 180, 77.5, "BanYa", "Get Up"));
		songs.add(new Song(0xB16, "CS059", 140, 82.5, "BanYa", "J Bong"));
		songs.add(new Song(0xB17, "CS058", 145, 82.5, "BanYa", "Hi Bi"));
		songs.add(new Song(0xB18, "CS057", 136, -250, "BanYa", "Solitary 2"));
		songs.add(new Song(0xB19, "CS056", 160, 90, "BanYa", "Canon D"));
		songs.add(new Song(0xC01, "CS065", 190, 52.5, "BanYa", "Beat of The War 2"));
		songs.add(new Song(0xC02, "CS064", 180, 40, "BanYa", "Moonlight"));
		songs.add(new Song(0xC03, "CS063", 195, 82.5, "BanYa", "Witch Doctor"));
		songs.add(new Song(0xC04, "CS062", 162, 800, "BanYa", "Love is a Danger Zone pt. 2"));
		songs.add(new Song(0xC05, "CS061", 136, 52.5, "BanYa", "Phantom"));
		songs.add(new Song(0xC06, "CS060", 145, 112.5, "BanYa", "Papa Gonzales"));
		songs.add(new Song(0xC1C04, "CS066", 162, 795, "BanYa", "Love is a Danger Zone pt.2 (Another)"));
		/* NX - NX2 */
		songs.add(new Song(0xD01, "CS076", 122, 85, "YAHPP", "Witch Doctor #1"));
		songs.add(new Song(0xD02, "CS075", 135, 0, "YAHPP", "Arch of Darkness"));
		songs.add(new Song(0xD03, "CS074", 210, 120, "YAHPP", "Chimera"));
		songs.add(new Song(0xD14, "CS073", 96, 130, "Banya Production", "2006. LOVE SONG"));
		songs.add(new Song(0xD15, "CS072", 120, 70, "Banya Production", "Do U Know That-Old School"));
		songs.add(new Song(0xD16, "CS071", 104, 48, "Banya Production", "Gun Rock"));
		songs.add(new Song(0xD17, "CS070", 168, 55, "Banya Production", "Bullfighter's Song"));
		songs.add(new Song(0xD18, "CS069", 92, 80, "Banya Production", "Ugly Dee"));
		songs.add(new Song(0xD28, "CS068", 170, 67, "YAHPP", "Final Audition Ep. 2-1"));
		songs.add(new Song(0xD30, "CS067", 200, 72.5, "YAHPP", "Final Audition Ep. 2-2"));
		songs.add(new Song(0xE01, "CS086", 136, 100, "YAHPP", "Solitary 1.5"));
		songs.add(new Song(0xE02, "CS085", 124, 45, "Banya Production", "Beat the ghost"));
		songs.add(new Song(0xE03, "CS084", 160, 73.5, "Banya Production", "Caprice of Otada"));
		songs.add(new Song(0xE04, "CS083", 130, 60, "Banya Production", "Money"));
		songs.add(new Song(0xE05, "CS082", 180, 74, "Banya Production", "Monkey Fingers 2"));
		songs.add(new Song(0xE12, "CS081", 162, 115, "YAHPP", "Faster Z"));
		songs.add(new Song(0xE13, "CS080", 153, 67.5, "YAHPP", "Pumptris Quattro"));
		songs.add(new Song(0xE13B, "CS087", 152, 85, "YAHPP", "Pumptris 8Bit ver."));
		songs.add(new Song(0xE23, "CS079", 120, 70, "Banya Production", "Guitar Man"));
		songs.add(new Song(0xE24, "CS078", 150, 67.5, "Banya Production", "Higgledy Piggledy"));
		songs.add(new Song(0xE25, "CS077", 120, 60, "Banya Production", "Jam O Beat"));
		/* NXA */
		songs.add(new Song(0xF01, "CS096", 170, 105, "YAHPP", "Blaze Emotion"));
		songs.add(new Song(0xF02, "CS095", 185, 167.5, "YAHPP", "CannonX.1"));
		songs.add(new Song(0xF03, "CS094", 128, 145, "YAHPP", "Chopsticks Challenge"));
		songs.add(new Song(0xF22, "CS093", 134, 80, "Banya Production", "The People didn't know"));
		songs.add(new Song(0xF23, "CS092", 180, 82.5, "Banya Production", "DJ Otada"));
		songs.add(new Song(0xF24, "CS091", 156, 110, "Banya Production", "K.O.A : Alice In Wonderworld"));
		songs.add(new Song(0xF25, "CS090", 136, 84.5, "Banya Production", "My Dreams"));
		songs.add(new Song(0xF26, "CS089", 104, 67, "Banya Production", "Toccata"));
		songs.add(new Song(0xF29, "CS088", 170, 110, "YAHPP", "Final Audition EP. 2-X"));
		/* Fiesta */
		songs.add(new Song(0x1001, "CS114", 190, -1142.5, "YAHPP", "XTREE"));
		songs.add(new Song(0x1002, "CS113", 158, -1320, "YAHPP", "Sorceress Eliese"));
		songs.add(new Song(0x1003, "CS112", 92, -510, "msgoon", "Betrayer -act.2-"));
		songs.add(new Song(0x1008, "CS111", 127, 77.5, "MAX", "U Got 2 Know"));
		songs.add(new Song(0x1013, "CS110", 150, 80, "SHK", "Destination"));
		songs.add(new Song(0x1017, "CS109", 200, 220, "Doin", "Vacuum"));
		songs.add(new Song(0x1021, "CS108", 96, 72.5, "Banya Production", "Do It Reggae Style"));
		songs.add(new Song(0x1022, "CS107", 152, 25, "Banya Production", "Xenesis"));
		songs.add(new Song(0x1023, "CS106", 160, 60, "Banya Production", "Arirang"));
		songs.add(new Song(0x1024, "CS105", 132, 70, "Banya Production", "Tek -Club Copenhagen-"));
		songs.add(new Song(0x1025, "CS104", 170, 92.5, "Banya Production", "Hello William"));
		songs.add(new Song(0x1026, "CS103", 130, 95, "Banya Production", "Turkey March -Minimal Tunes-"));
		songs.add(new Song(0x1027, "CS102", 164, 105, "Banya Production", "Get Up (and go)"));
		songs.add(new Song(0x1028, "CS101", 140, 70, "Banya Production", "Phantom -Intermezzo-"));
		songs.add(new Song(0x1029, "CS100", 128, 125, "Banya Production", "Mission Possible -Blow Back-"));
		songs.add(new Song(0x1030, "CS099", 116, -460, "Banya Production", "Pumping Jumping"));
		songs.add(new Song(0x1094, "CS098", 145, 963, "Doin", "Tepris"));
		songs.add(new Song(0x1095, "CS097", 185, 75, "Doin", "Napalm"));
		/* Fiesta EX */
		songs.add(new Song(0x1101, "CS127", 203, 65, "Doin", "Cleaner"));
		songs.add(new Song(0x1102, "CS126", 180.4, 365, "Doin", "Interference"));
		songs.add(new Song(0x1103, "CS125", 135, 55, "SHK", "Reality"));
		songs.add(new Song(0x1104, "CS124", 170, 48, "SHK", "Take Out"));
		songs.add(new Song(0x1105, "CS123", 128, 977, "MAX & Rorychesell (SID-SOUND)", "Butterfly"));
		songs.add(new Song(0x1106, "CS122", 155, 65, "MAX", "Overblow"));
		songs.add(new Song(0x1107, "CS121", 128, 55, "MAX", "We Got 2 Know"));
		songs.add(new Song(0x1123, "CS120", 145, 80, "Banya Production", "Hungarian Dance V"));
		songs.add(new Song(0x1124, "CS119", 130, 90, "Banya Production", "The Devil"));
		songs.add(new Song(0x1126, "CS118", 135, 50, "SHK", "Native"));
		songs.add(new Song(0x1151, "CS128", 140, 45, "A.V.", "Night Duty"));
		songs.add(new Song(0x1152, "CS129", 160.81, 90, "V.A.", "Pavane"));
		songs.add(new Song(0x1153, "CS117", 200, 80, "Doin", "Pine Nut"));
		songs.add(new Song(0x1154, "CS116", 170, 112, "Doin", "ASDF"));
		songs.add(new Song(0x1156, "CS115", 155, -738, "Doin", "Says"));
		songs.add(new Song(0x1160, "CS130", 140, -650, "MAX & Seorryang (SID-SOUND)", "Jonathan's Dream"));
		/* Fiesta 2 - Crossovers */
		songs.add(new Song(0x1201, "CS142", 196, 55, "Affinity", "Monolith"));
		songs.add(new Song(0x1208, "CS141", 112, -515, "Celldweller", "Switchback"));
		songs.add(new Song(0x1209, "CS140", 133, 55, "Coconut", "Ladybug"));
		songs.add(new Song(0x1212, "CS139", 190, 95, "Diclonius Kid", "Hardkore of the North"));
		songs.add(new Song(0x1218, "CS138", 128, 80, "Future Funk Squad", "Rippin' It Up"));
		songs.add(new Song(0x1219, "CS137", 150, 90, "Hi-G", "Tribe Attacker"));
		songs.add(new Song(0x1221, "CS136", 180, 42.5, "Inspector K", "Virtual Emotion"));
		songs.add(new Song(0x1226, "CS135", 185, 695, "KURi-ZiLL", "Heel and Toe"));
		songs.add(new Song(0x1230, "CS133", 125, 47.5, "Sanxion7", "Rainspark"));
		songs.add(new Song(0x1237, "CS132", 142, 75, "Stian K", "Be Alive (Raaban Inc. Mix)"));
		songs.add(new Song(0x1241, "CS131", 155, 45, "Zircon", "Star Command"));
		/* Fiesta 2 - Originals */
		songs.add(new Song(0x1305, "CS152", 162, 807, "DM Ashura", "Elise"));
		songs.add(new Song(0x1306, "CS151", 220, 52, "BanYa & DM Ashura", "Ignis Fatuus (DM Ashura Mix)"));
		songs.add(new Song(0x1307, "CS150", 155, 212, "BanYa & Cranky", "Love Is A Danger Zone (Cranky Mix)"));
		songs.add(new Song(0x1308, "CS149", 175, 27, "BanYa & SynthWulf", "Hypnosis (SynthWulf Mix)"));
		songs.add(new Song(0x1309, "CS148", 200, 32, "Doin", "FFF"));
		songs.add(new Song(0x1310, "CS147", 145, 36, "SHK", "Unique"));
		songs.add(new Song(0x1311, "CS146", 183, 47, "MAX", "Accident"));
		songs.add(new Song(0x1312, "CS145", 180, -138, "MAX", "D"));
		songs.add(new Song(0x1313, "CS144", 128, 19.5, "MAX", "U Got Me Rocking"));
		/* Prime */
		// XXX no ucs songlist released as of yet
		/* id indexes */
		for (Song song : songs)
			songIndexes.put(song.id, song);
		/* 1st - 3rd */
		final List<Song> _1stTo3rd = new ArrayList<>();
		_1stTo3rd.add(songIndexes.get(0x204));
		_1stTo3rd.add(songIndexes.get(0x205));
		_1stTo3rd.add(songIndexes.get(0x301));
		_1stTo3rd.add(songIndexes.get(0x302));
		_1stTo3rd.add(songIndexes.get(0x303));
		_1stTo3rd.add(songIndexes.get(0x306));
		_1stTo3rd.add(songIndexes.get(0x307));
		_1stTo3rd.add(songIndexes.get(0x308));
		_1stTo3rd.add(songIndexes.get(0x309));
		_1stTo3rd.add(songIndexes.get(0x310));
		_1stTo3rd.add(songIndexes.get(0x311));
		/* SE - Extra */
		final List<Song> seToExtra = new ArrayList<>();
		seToExtra.add(songIndexes.get(0x401));
		seToExtra.add(songIndexes.get(0x403));
		seToExtra.add(songIndexes.get(0x404));
		seToExtra.add(songIndexes.get(0x405));
		seToExtra.add(songIndexes.get(0x410));
		seToExtra.add(songIndexes.get(0x501));
		seToExtra.add(songIndexes.get(0x502));
		seToExtra.add(songIndexes.get(0x503));
		seToExtra.add(songIndexes.get(0x504));
		seToExtra.add(songIndexes.get(0x505));
		seToExtra.add(songIndexes.get(0x911));
		seToExtra.add(songIndexes.get(0x922));
		/* Rebirth - Prex3 */
		final List<Song> rebirthToPrex3 = new ArrayList<>();
		rebirthToPrex3.add(songIndexes.get(0x701));
		rebirthToPrex3.add(songIndexes.get(0x702));
		rebirthToPrex3.add(songIndexes.get(0x703));
		rebirthToPrex3.add(songIndexes.get(0x704));
		rebirthToPrex3.add(songIndexes.get(0x705));
		rebirthToPrex3.add(songIndexes.get(0x706));
		rebirthToPrex3.add(songIndexes.get(0x707));
		rebirthToPrex3.add(songIndexes.get(0x708));
		rebirthToPrex3.add(songIndexes.get(0x709));
		rebirthToPrex3.add(songIndexes.get(0x710));
		rebirthToPrex3.add(songIndexes.get(0x711));
		rebirthToPrex3.add(songIndexes.get(0x712));
		rebirthToPrex3.add(songIndexes.get(0x713));
		rebirthToPrex3.add(songIndexes.get(0x714));
		rebirthToPrex3.add(songIndexes.get(0x715));
		rebirthToPrex3.add(songIndexes.get(0x716));
		rebirthToPrex3.add(songIndexes.get(0x717));
		rebirthToPrex3.add(songIndexes.get(0x718));
		rebirthToPrex3.add(songIndexes.get(0x735));
		rebirthToPrex3.add(songIndexes.get(0x736));
		rebirthToPrex3.add(songIndexes.get(0x802));
		rebirthToPrex3.add(songIndexes.get(0x807));
		rebirthToPrex3.add(songIndexes.get(0x811));
		rebirthToPrex3.add(songIndexes.get(0x820));
		rebirthToPrex3.add(songIndexes.get(0x826));
		/* Exceed - Zero */
		final List<Song> exceedToZero = new ArrayList<>();
		exceedToZero.add(songIndexes.get(0xA01));
		exceedToZero.add(songIndexes.get(0xA02));
		exceedToZero.add(songIndexes.get(0xA03));
		exceedToZero.add(songIndexes.get(0xA04));
		exceedToZero.add(songIndexes.get(0xA05));
		exceedToZero.add(songIndexes.get(0xA06));
		exceedToZero.add(songIndexes.get(0xA07));
		exceedToZero.add(songIndexes.get(0xB16));
		exceedToZero.add(songIndexes.get(0xB17));
		exceedToZero.add(songIndexes.get(0xB18));
		exceedToZero.add(songIndexes.get(0xB19));
		exceedToZero.add(songIndexes.get(0xC01));
		exceedToZero.add(songIndexes.get(0xC02));
		exceedToZero.add(songIndexes.get(0xC03));
		exceedToZero.add(songIndexes.get(0xC04));
		exceedToZero.add(songIndexes.get(0xC05));
		exceedToZero.add(songIndexes.get(0xC06));
		exceedToZero.add(songIndexes.get(0xC1C04));
		/* NX - NX2 */
		final List<Song> nxToNx2 = new ArrayList<>();
		nxToNx2.add(songIndexes.get(0xD01));
		nxToNx2.add(songIndexes.get(0xD02));
		nxToNx2.add(songIndexes.get(0xD03));
		nxToNx2.add(songIndexes.get(0xD14));
		nxToNx2.add(songIndexes.get(0xD15));
		nxToNx2.add(songIndexes.get(0xD16));
		nxToNx2.add(songIndexes.get(0xD17));
		nxToNx2.add(songIndexes.get(0xD18));
		nxToNx2.add(songIndexes.get(0xD28));
		nxToNx2.add(songIndexes.get(0xD30));
		nxToNx2.add(songIndexes.get(0xE01));
		nxToNx2.add(songIndexes.get(0xE02));
		nxToNx2.add(songIndexes.get(0xE03));
		nxToNx2.add(songIndexes.get(0xE04));
		nxToNx2.add(songIndexes.get(0xE05));
		nxToNx2.add(songIndexes.get(0xE12));
		nxToNx2.add(songIndexes.get(0xE13));
		nxToNx2.add(songIndexes.get(0xE13B));
		nxToNx2.add(songIndexes.get(0xE23));
		nxToNx2.add(songIndexes.get(0xE24));
		nxToNx2.add(songIndexes.get(0xE25));
		/* NXA */
		final List<Song> nxa = new ArrayList<>();
		nxa.add(songIndexes.get(0xF01));
		nxa.add(songIndexes.get(0xF02));
		nxa.add(songIndexes.get(0xF03));
		nxa.add(songIndexes.get(0xF22));
		nxa.add(songIndexes.get(0xF23));
		nxa.add(songIndexes.get(0xF24));
		nxa.add(songIndexes.get(0xF25));
		nxa.add(songIndexes.get(0xF26));
		nxa.add(songIndexes.get(0xF29));
		/* Fiesta */
		final List<Song> fiesta = new ArrayList<>();
		fiesta.add(songIndexes.get(0x1001));
		fiesta.add(songIndexes.get(0x1002));
		fiesta.add(songIndexes.get(0x1003));
		fiesta.add(songIndexes.get(0x1008));
		fiesta.add(songIndexes.get(0x1013));
		fiesta.add(songIndexes.get(0x1017));
		fiesta.add(songIndexes.get(0x1021));
		fiesta.add(songIndexes.get(0x1022));
		fiesta.add(songIndexes.get(0x1023));
		fiesta.add(songIndexes.get(0x1024));
		fiesta.add(songIndexes.get(0x1025));
		fiesta.add(songIndexes.get(0x1026));
		fiesta.add(songIndexes.get(0x1027));
		fiesta.add(songIndexes.get(0x1028));
		fiesta.add(songIndexes.get(0x1029));
		fiesta.add(songIndexes.get(0x1030));
		fiesta.add(songIndexes.get(0x1094));
		fiesta.add(songIndexes.get(0x1095));
		/* Fiesta EX */
		final List<Song> fiestaEx = new ArrayList<>();
		fiestaEx.add(songIndexes.get(0x1101));
		fiestaEx.add(songIndexes.get(0x1102));
		fiestaEx.add(songIndexes.get(0x1103));
		fiestaEx.add(songIndexes.get(0x1104));
		fiestaEx.add(songIndexes.get(0x1105));
		fiestaEx.add(songIndexes.get(0x1106));
		fiestaEx.add(songIndexes.get(0x1107));
		fiestaEx.add(songIndexes.get(0x1123));
		fiestaEx.add(songIndexes.get(0x1124));
		fiestaEx.add(songIndexes.get(0x1126));
		fiestaEx.add(songIndexes.get(0x1151));
		fiestaEx.add(songIndexes.get(0x1152));
		fiestaEx.add(songIndexes.get(0x1153));
		fiestaEx.add(songIndexes.get(0x1154));
		fiestaEx.add(songIndexes.get(0x1156));
		fiestaEx.add(songIndexes.get(0x1160));
		/* Fiesta 2 - Crossovers */
		final List<Song> fiesta2Crossovers = new ArrayList<>();
		fiesta2Crossovers.add(songIndexes.get(0x1201));
		fiesta2Crossovers.add(songIndexes.get(0x1208));
		fiesta2Crossovers.add(songIndexes.get(0x1209));
		fiesta2Crossovers.add(songIndexes.get(0x1212));
		fiesta2Crossovers.add(songIndexes.get(0x1218));
		fiesta2Crossovers.add(songIndexes.get(0x1219));
		fiesta2Crossovers.add(songIndexes.get(0x1221));
		fiesta2Crossovers.add(songIndexes.get(0x1226));
		fiesta2Crossovers.add(songIndexes.get(0x1230));
		fiesta2Crossovers.add(songIndexes.get(0x1237));
		fiesta2Crossovers.add(songIndexes.get(0x1241));
		/* Fiesta 2 - Originals */
		final List<Song> fiesta2Originals = new ArrayList<>();
		fiesta2Originals.add(songIndexes.get(0x1305));
		fiesta2Originals.add(songIndexes.get(0x1306));
		fiesta2Originals.add(songIndexes.get(0x1307));
		fiesta2Originals.add(songIndexes.get(0x1308));
		fiesta2Originals.add(songIndexes.get(0x1309));
		fiesta2Originals.add(songIndexes.get(0x1310));
		fiesta2Originals.add(songIndexes.get(0x1311));
		fiesta2Originals.add(songIndexes.get(0x1312));
		fiesta2Originals.add(songIndexes.get(0x1313));
		/* Prime */
		//final List<Song> prime = new ArrayList<>();
		songGroups.add(new Group("1st - 3rd", _1stTo3rd));
		songGroups.add(new Group("SE - Extra", seToExtra));
		songGroups.add(new Group("Rebirth - Prex3", rebirthToPrex3));
		songGroups.add(new Group("Exceed - Zero", exceedToZero));
		songGroups.add(new Group("NX - NX2", nxToNx2));
		songGroups.add(new Group("NXA", nxa));
		songGroups.add(new Group("Fiesta", fiesta));
		songGroups.add(new Group("Fiesta EX", fiestaEx));
		songGroups.add(new Group("Fiesta 2 - Crossovers", fiesta2Crossovers));
		songGroups.add(new Group("Fiesta 2 - Originals", fiesta2Originals));
		//songGroups.add(new Group("Prime", prime));
	}
	
	/**
	 * Get a list of available songs
	 * @return A list of available songs
	 */
	@GET
	public Response index() throws Exception
	{
		return Response.ok(songs).build();
	}
	
	/**
	 * Get a single song object
	 * @param id of the song
	 * @return a single song object
	 */
	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") String id)
	{
		try
		{
			Integer intID = Integer.parseInt(id, 16);
			Song song = songIndexes.get(intID);
			if (song == null)
				throw new ApiException("That song does not exist", Status.NOT_FOUND.getStatusCode());
			return Response.ok(song).build();
		}
		catch (Exception ex)
		{
			if (ex instanceof ApiException)
				throw ex;
			throw new ApiException(ex.toString(), Status.BAD_REQUEST.getStatusCode());
		}
	}
	
	@GET
	@Path("groups")
	public Response groups() throws Exception
	{
		return Response.ok(songGroups).build();
	}
}
