// Lab_2
//This is my lab task in which i made a simple interpreter using java


import java.util.*;
import java.io.*;

public class Interpreter {
	
	Map<String, Integer> m = new HashMap<String, Integer>(); //one-one maping between name and value of variable  
	@SuppressWarnings("null")

	public void read_file(String file_Name) throws IOException{  //rading and parsing file
		String operator = null;   //use to svae operator as String
		String variable_name = null;
		int variable = 0,variable_1=0,variable_2=0;
		FileReader fr = new FileReader(file_Name);  //file reading using buffer reader
		BufferedReader br = new BufferedReader(fr);
		String buffer;              //buffer used to read line by line
		int z=0;
		String var_name = null; 
		int var_value =0;
		while((buffer= br.readLine())!=null){   //read file line by line
			z=0;
			operator=null;            //use to store operator sign
			boolean flag_ope=false;   //flag use so no consective operand or variavle 
			String[] token = buffer.split(" ");   //split each line by space as delimiter
			if(token[0].compareTo("Let")==0){      //if line start by Let mean its intializing a variable
				z++;
				if(z>=token.length){
					System.out.println("Syntax error");
					System.exit(1);
				}
				var_name = token[z];
				if(Character.isLetter(var_name.charAt(0))){
					z++;
					if(z>=token.length){
						System.out.println("Syntax error");
						System.exit(1);
					}
					if(m.containsKey(token[z-1])){
						System.out.println("Syntax Error "+token[z-1]+" Already Declared.\n");
						System.exit(1);
					}
					if(token[z].compareTo("=")==0){
						if(z<token.length){
							z++;
							if(z<=token.length){
								int is_number=1;
								for(int i=0;i<token[z].length();i++){
									if(!Character.isDigit(token[z].charAt(i)))
										is_number=0;
								}
								if(is_number==1){
									var_value = Integer.parseInt(token[z]);
									m.put(var_name, var_value);
								}
								else{
									System.out.println("Cant initalized character");
								}
							}
							else
								System.out.println("Syntax error");
						}
						else
							System.out.println("Syntax error");
					}
					else{
						System.out.println("Error: Unintialized variable;");
					}
				}	
			}
			else if(token[z].compareTo("Print")==0){
				z++;
				if(m.containsKey(token[z])){
					System.out.println("The value of "+token[z]+" = "+m.get(token[z]));
				}
				else{
					System.out.println(token[z]+" is undecalared identifier");
				}
			}
			else{
				if(Character.isLetter(token[z].charAt(0))){
					if(m.containsKey(token[z])){
						variable_name = token[z];
						variable = m.get(variable_name);
						
						//z++;
					}
					else
						System.out.println(token[z]+" is undecalared identifier");
					z++;
					if(token[z].compareTo("=")==0){
						z++;
						for(int x=z;x<token.length;x++){
							if(Character.isLetter(token[x].charAt(0)) || Character.isDigit(token[x].charAt(0)) && flag_ope == false){
								if(Character.isDigit(token[x].charAt(0)))
									variable_1 = Integer.parseInt(token[x]);
								else if(Character.isLetter(token[x].charAt(0))){
									if(m.containsKey(token[x])){
										variable_1 = m.get(token[x]);
										m.put(variable_name,variable_1);
									}
									else
										System.out.println(token[z]+" is undecalared identifier");
								}
								if(operator != null){
									variable_2 = operate(variable ,variable_1,operator);
									variable = variable_2;
									m.put(variable_name, variable);
								}
								flag_ope =true;
							}
							else if(flag_ope == true){
								if(token[x].compareTo("+")==0 ||token[x].compareTo("-")==0 || token[x].compareTo("*")==0||token[x].compareTo("/")==0){
									operator = token[x];
									if((x+1)>=token.length){
										System.out.println("Syntax Error");
										System.exit(1);
									}
								}
								flag_ope = false;
							}
						}
						
					}
					else
						System.out.println("Syntax Error:");
						
				}
			}
		}
	}
	private int  operate(int operand_1,int operand_2,String operator){
		switch(operator){
		case "+":
			return (operand_1+operand_2);
		case "-":
			return (operand_1-operand_2);
		case "*":
			return (operand_1*operand_2);
		case "/":
			return (operand_1/operand_2);
		default:
		}
		
		return  0;
	}
	public static void main(String[] args) throws IOException {
		Interpreter i = new Interpreter();
		Interpreter i1 = new Interpreter();
		Interpreter i2 = new Interpreter();
		i.read_file("testFile_1.txt");
		i1.read_file("testFile_2.txt");
		i2.read_file("testFile_3.txt");
		// TODO Auto-generated method stub
	}

}

