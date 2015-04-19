package com.troncodroide.pesoppo.exceptions;

public class SqlExceptions {
	public static class IdNotFoundException extends Exception{
		private static final long serialVersionUID = 1L;
	}
	public static class DuplicatedIdException extends Exception{
		private static final long serialVersionUID = 1L;
	}
	public static class UniqueKeyException extends Exception{
		
		private static final long serialVersionUID = 1L;
	}

}
