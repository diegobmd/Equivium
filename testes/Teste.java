package testes;



public class Teste {

	public static void main(String[] args) {
	   
      try {
         
         //String a[] = { "111" };

         //String s = String.format("Welcome %s at %s", a);
         //System.out.println(s);
         
         
//         NumberFormat formatter = new DecimalFormat("(###)####-####");
//         String s = formatter.format(11145552631.0);
//         System.out.println(s);

      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

	   
//		
//		try {
//			XFileUtil.copiarArquivo("c:\\de.JPG", "c:\\para.JPG");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(XFormatUtil.formataData(new Date(), "dd/MM/yyy HH:mm:ss"));
		
		//System.out.println(XFileUtil.getCurrentPath() + "\\log\\" + XFormatUtil.formataData(new Date(), "ddMMyyyy")+".log");
		
//		XBanco.setBanco(new XHSQLDB("banco/hsqldb/equivium", "sa", "") );
//		
//		XTrataException.setShowException(false);
//		XTrataException.printStackTrace(new Exception("sadasdsad"));
//		System.out.println("Passou");
		
//		try {
//			new XAudio().play("C:\\Documents and Settings\\Diego\\Meus documentos\\Minhas músicas\\AcDc\\AcDc - back in black.mp3");
//		} catch (NoPlayerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedAudioFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (LineUnavailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		System.out.println( ((double)2/4)*100 );
		
//		XBanco.setBanco(new XHSQLDB("banco/hsqldb/equivium", "sa", "") );
//
//		
//		try {
//			PreparedStatement p = XBanco.getPrepareStatement("SELECT Sub_Codigo 	as Codigo , Sub_Nome 	as Nome FROM Sub_Fases LIMIT ?");
//			
//			p.setInt(1, 1);
//			
//			ResultSet r = p.getResultSet();
//			
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
//		 JasperDesign jasperDesign;
//		try {
//			 JasperReport jasperReport = JasperCompileManager.compileReport("./relatorios/Realisa Sessao.jrxml");
//			 
//			 HashMap<String, String> parameters = new HashMap<String, String>();           
//			 parameters.put("sql"," WHERE REL_REALIZA = 1");  
//			   
//			 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,  parameters, XBanco.getConexao()); 
//			   
//			 JasperViewer.viewReport(jasperPrint,false); 
//			 
//		} catch (JRException e) {
//			e.printStackTrace();
//		}  
//
//		ArrayList<String> a = new ArrayList<String>();
//		
//		a.add("1");
//		a.add("2");
//		a.add("3");
//		a.add("4");
//		
//		XPermutacao<String> permutacao = new XPermutacao<String>(a, 1);
//		permutacao.setCircular(false);
//		String p = "";
//		//permutacao.shuffle();
//		for (ArrayList<String> k : permutacao.getAll() ){
//			p = "";
//			p = p + k.get(0) + " - ";
////			p = p + k.get(1) + " - ";;
////			p = p + k.get(2) + " - ";;
////			p = p + k.get(3);;
//			
//			System.out.println(p);;
//		}
//		
		
	}
	
}
