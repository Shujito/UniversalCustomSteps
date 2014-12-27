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
import org.shujito.ucs.models.Song;

@Path("/songs")
@Produces(MediaType.APPLICATION_JSON)
public class Songs
{
	public static final String TAG = Songs.class.getSimpleName();
	public static final List<Song> songs;
	public static final Map<Integer, Song> songIndexes;
	static
	{
		songs = new ArrayList<>();
		songIndexes = new HashMap<Integer, Song>();
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
		songIndexes.put(0x204, songs.get(0));
		songIndexes.put(0x205, songs.get(1));
		songIndexes.put(0x301, songs.get(2));
		songIndexes.put(0x302, songs.get(3));
		songIndexes.put(0x303, songs.get(4));
		songIndexes.put(0x306, songs.get(5));
		songIndexes.put(0x307, songs.get(6));
		songIndexes.put(0x308, songs.get(7));
		songIndexes.put(0x309, songs.get(8));
		songIndexes.put(0x310, songs.get(9));
		songIndexes.put(0x311, songs.get(10));
		songIndexes.put(0x401, songs.get(11));
		songIndexes.put(0x403, songs.get(12));
		songIndexes.put(0x404, songs.get(13));
		songIndexes.put(0x405, songs.get(14));
		songIndexes.put(0x410, songs.get(15));
		songIndexes.put(0x501, songs.get(16));
		songIndexes.put(0x502, songs.get(17));
		songIndexes.put(0x503, songs.get(18));
		songIndexes.put(0x504, songs.get(19));
		songIndexes.put(0x505, songs.get(20));
		songIndexes.put(0x911, songs.get(21));
		songIndexes.put(0x922, songs.get(22));
		songIndexes.put(0x701, songs.get(23));
		songIndexes.put(0x702, songs.get(24));
		songIndexes.put(0x703, songs.get(25));
		songIndexes.put(0x704, songs.get(26));
		songIndexes.put(0x705, songs.get(27));
		songIndexes.put(0x706, songs.get(28));
		songIndexes.put(0x707, songs.get(29));
		songIndexes.put(0x708, songs.get(30));
		songIndexes.put(0x709, songs.get(31));
		songIndexes.put(0x710, songs.get(32));
		songIndexes.put(0x711, songs.get(33));
		songIndexes.put(0x712, songs.get(34));
		songIndexes.put(0x713, songs.get(35));
		songIndexes.put(0x714, songs.get(36));
		songIndexes.put(0x715, songs.get(37));
		songIndexes.put(0x716, songs.get(38));
		songIndexes.put(0x717, songs.get(39));
		songIndexes.put(0x718, songs.get(40));
		songIndexes.put(0x735, songs.get(41));
		songIndexes.put(0x736, songs.get(42));
		songIndexes.put(0x802, songs.get(43));
		songIndexes.put(0x807, songs.get(44));
		songIndexes.put(0x811, songs.get(45));
		songIndexes.put(0x820, songs.get(46));
		songIndexes.put(0x826, songs.get(47));
		songIndexes.put(0xA01, songs.get(48));
		songIndexes.put(0xA02, songs.get(49));
		songIndexes.put(0xA03, songs.get(50));
		songIndexes.put(0xA04, songs.get(51));
		songIndexes.put(0xA05, songs.get(52));
		songIndexes.put(0xA06, songs.get(53));
		songIndexes.put(0xA07, songs.get(54));
		songIndexes.put(0xB16, songs.get(55));
		songIndexes.put(0xB17, songs.get(56));
		songIndexes.put(0xB18, songs.get(57));
		songIndexes.put(0xB19, songs.get(58));
		songIndexes.put(0xC01, songs.get(59));
		songIndexes.put(0xC02, songs.get(60));
		songIndexes.put(0xC03, songs.get(61));
		songIndexes.put(0xC04, songs.get(62));
		songIndexes.put(0xC05, songs.get(63));
		songIndexes.put(0xC06, songs.get(64));
		songIndexes.put(0xC1C04, songs.get(65));
		songIndexes.put(0xD01, songs.get(66));
		songIndexes.put(0xD02, songs.get(67));
		songIndexes.put(0xD03, songs.get(68));
		songIndexes.put(0xD14, songs.get(69));
		songIndexes.put(0xD15, songs.get(70));
		songIndexes.put(0xD16, songs.get(71));
		songIndexes.put(0xD17, songs.get(72));
		songIndexes.put(0xD18, songs.get(73));
		songIndexes.put(0xD28, songs.get(74));
		songIndexes.put(0xD30, songs.get(75));
		songIndexes.put(0xE01, songs.get(76));
		songIndexes.put(0xE02, songs.get(77));
		songIndexes.put(0xE03, songs.get(78));
		songIndexes.put(0xE04, songs.get(79));
		songIndexes.put(0xE05, songs.get(80));
		songIndexes.put(0xE12, songs.get(81));
		songIndexes.put(0xE13, songs.get(82));
		songIndexes.put(0xE13B, songs.get(83));
		songIndexes.put(0xE23, songs.get(84));
		songIndexes.put(0xE24, songs.get(85));
		songIndexes.put(0xE25, songs.get(86));
		songIndexes.put(0xF01, songs.get(87));
		songIndexes.put(0xF02, songs.get(88));
		songIndexes.put(0xF03, songs.get(89));
		songIndexes.put(0xF22, songs.get(90));
		songIndexes.put(0xF23, songs.get(91));
		songIndexes.put(0xF24, songs.get(92));
		songIndexes.put(0xF25, songs.get(93));
		songIndexes.put(0xF26, songs.get(94));
		songIndexes.put(0xF29, songs.get(95));
		songIndexes.put(0x1001, songs.get(96));
		songIndexes.put(0x1002, songs.get(97));
		songIndexes.put(0x1003, songs.get(98));
		songIndexes.put(0x1008, songs.get(99));
		songIndexes.put(0x1013, songs.get(100));
		songIndexes.put(0x1017, songs.get(101));
		songIndexes.put(0x1021, songs.get(102));
		songIndexes.put(0x1022, songs.get(103));
		songIndexes.put(0x1023, songs.get(104));
		songIndexes.put(0x1024, songs.get(105));
		songIndexes.put(0x1025, songs.get(106));
		songIndexes.put(0x1026, songs.get(107));
		songIndexes.put(0x1027, songs.get(108));
		songIndexes.put(0x1028, songs.get(109));
		songIndexes.put(0x1029, songs.get(110));
		songIndexes.put(0x1030, songs.get(111));
		songIndexes.put(0x1094, songs.get(112));
		songIndexes.put(0x1095, songs.get(113));
		songIndexes.put(0x1101, songs.get(114));
		songIndexes.put(0x1102, songs.get(115));
		songIndexes.put(0x1103, songs.get(116));
		songIndexes.put(0x1104, songs.get(117));
		songIndexes.put(0x1105, songs.get(118));
		songIndexes.put(0x1106, songs.get(119));
		songIndexes.put(0x1107, songs.get(120));
		songIndexes.put(0x1123, songs.get(121));
		songIndexes.put(0x1124, songs.get(122));
		songIndexes.put(0x1126, songs.get(123));
		songIndexes.put(0x1151, songs.get(124));
		songIndexes.put(0x1152, songs.get(125));
		songIndexes.put(0x1153, songs.get(126));
		songIndexes.put(0x1154, songs.get(127));
		songIndexes.put(0x1156, songs.get(128));
		songIndexes.put(0x1160, songs.get(129));
		songIndexes.put(0x1201, songs.get(130));
		songIndexes.put(0x1208, songs.get(131));
		songIndexes.put(0x1209, songs.get(132));
		songIndexes.put(0x1212, songs.get(133));
		songIndexes.put(0x1218, songs.get(134));
		songIndexes.put(0x1219, songs.get(135));
		songIndexes.put(0x1221, songs.get(136));
		songIndexes.put(0x1226, songs.get(137));
		songIndexes.put(0x1230, songs.get(138));
		songIndexes.put(0x1237, songs.get(139));
		songIndexes.put(0x1241, songs.get(140));
		songIndexes.put(0x1305, songs.get(141));
		songIndexes.put(0x1306, songs.get(142));
		songIndexes.put(0x1307, songs.get(143));
		songIndexes.put(0x1308, songs.get(144));
		songIndexes.put(0x1309, songs.get(145));
		songIndexes.put(0x1310, songs.get(146));
		songIndexes.put(0x1311, songs.get(147));
		songIndexes.put(0x1312, songs.get(148));
		songIndexes.put(0x1313, songs.get(149));
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
}
