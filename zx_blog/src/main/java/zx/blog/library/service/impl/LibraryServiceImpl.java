package zx.blog.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.library.dao.LibraryDao;
import zx.blog.library.service.LibraryService;

@Component
public class LibraryServiceImpl implements LibraryService{
	@Autowired
	LibraryDao libraryDao;
	
	
}
