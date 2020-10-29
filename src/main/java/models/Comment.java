package models;

import com.google.protobuf.Timestamp;

public class Comment {
	int id;
	User user;
	Playlist playlist;
	String message;
	Timestamp instant;
}
