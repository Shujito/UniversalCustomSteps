window.Songs = function Songs() {
	// map things
}

window.Songs.render = function Render(callback) {
	Utils.get('/api/songs/groups',null,function(err,data){
		if (err) return;
		if (!callback) {
			console.error('no callback!');
			return;
		}
		// apend things here
		var songsTemplate = $('#songs-template');
		// templates
		var songsGroupTemplate = $('#songs-group-template');
		var songsSongTemplate = $('#songs-song-template');
		// appendable
		var container = $(songsTemplate.text())
		data.forEach(function(group) {
			var newGroup = $(songsGroupTemplate.text())
			var groupName = newGroup.find('#group-name').removeAttr('id');
			var groupSongsCount = newGroup.find('#group-song-count').removeAttr('id');
			var groupSongs = newGroup.find('#group-songs').removeAttr('id');
			groupName.text(group.name);
			groupSongsCount.text(group.songs.length + ' songs');
			group.songs.forEach(function(song){
				var newSong = $(songsSongTemplate.text());
				var songName = newSong.find('#song-name').removeAttr('id');
				var songArtist = newSong.find('#song-artist').removeAttr('id');
				var songUrl = newSong.find('#song-url').removeAttr('id');
				songUrl.attr('href','/song/' + song.id.toString(16))
				songName.text(song.name);
				songArtist.text(song.artist);
				newSong.appendTo(groupSongs);
			});
			newGroup.appendTo(container);
		});
		// I want the whole thing, have to put it there...
		var html = $(document.createElement('div'));
		container.appendTo(html);
		callback(html.html());
	});
}