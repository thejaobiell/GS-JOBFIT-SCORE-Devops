package com.gs.fiap.jobfitscore;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {
	
	private static final String RESET = "\u001B[0m";
	private static final String GREEN = "\u001B[32m";
	private static final String BLUE = "\u001B[34m";
	private static final String CYAN = "\u001B[36m";
	private static final String RED = "\u001B[31m";
	private static final String BOLD = "\u001B[1m";
	
	@EventListener( ApplicationReadyEvent.class )
	public void logWhenReady() {
		printBanner();
	}
	
	private void printBanner() {
		clearConsole();
		System.out.println( GREEN + BOLD + """
				 ██████╗ ███╗   ██╗██╗     ██╗███╗   ██╗███████╗██╗
				██╔═══██╗████╗  ██║██║     ██║████╗  ██║██╔════╝██║
				██║   ██║██╔██╗ ██║██║     ██║██╔██╗ ██║█████╗  ██║
				██║   ██║██║╚██╗██║██║     ██║██║╚██╗██║██╔══╝  ╚═╝
				╚██████╔╝██║ ╚████║███████╗██║██║ ╚████║███████╗██╗
				 ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚═╝╚═╝  ╚═══╝╚══════╝╚═╝
				""" + RESET );
		
		System.out.println( CYAN + "Clique aqui para acessar o aplicação:   " + RED + "http://localhost:8080" + RESET + "\n" );
		System.out.println( BLUE + "Clique aqui para acessar o Swagger UI:   " + RED + "http://localhost:8080/swagger-ui/index.html" + RESET );
		System.out.println( RED + "_____________________________________________________________" + RESET + "\n" );
	}
	
	private void clearConsole() {
		try {
			final String os = System.getProperty( "os.name" );
			if ( os.contains( "Windows" ) ) {
				new ProcessBuilder( "cmd", "/c", "cls" ).inheritIO().start().waitFor();
			} else {
				System.out.print( "\033[H\033[2J" );
				for (int i = 0; i < 50; i++) {
					System.out.println();
				}
				System.out.flush();
			}
		} catch ( Exception e ) {
		}
	}
}
