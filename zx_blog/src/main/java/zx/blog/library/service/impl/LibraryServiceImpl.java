package zx.blog.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.library.service.LibraryService;
import zx.blog.mapper.LibraryMapper;

@Component
public class LibraryServiceImpl implements LibraryService{
	@Autowired
	LibraryMapper libraryDao;
	
	
}
