package com.Premate.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

public class AdminBlog {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int blog_id;
	@Lob
	private byte[] blog_img;

}
