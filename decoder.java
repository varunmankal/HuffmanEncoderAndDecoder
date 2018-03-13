import java.io.BufferedInputStream;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


class Huffman_Node1
{
	int data;
	long frequency;
	Huffman_Node1 left;
	Huffman_Node1 right;
	Huffman_Node1(int key,long freq_value)
	{
			data=key;
			frequency=freq_value;
			left=null;
			right=null;
	}
	
	Huffman_Node1(long freq_value,Huffman_Node1 l,Huffman_Node1 r)
	{
		frequency=freq_value;
		left=l;
		right=r;
	}
	
}
public class decoder {

	Huffman_Node1 decode_root=new Huffman_Node1(-1,-1);
	//Huffman_Node decode_temp;
	
	
	void generate_decode_tree(String code_table) throws IOException
	{
		System.out.println("creating decode tree");
		FileReader fr1=new FileReader(code_table);
		BufferedReader br1=new BufferedReader(fr1);
		String s;
		//int[] decode_tree=new int[1000000];
		
		
		Huffman_Node1 decode_temp=decode_root;
		while((s=br1.readLine())!=null)
		{
			String s1[]=s.split(" ");
			
			//System.out.println("s1[1] is "+s1[1]+"\t"+s1[0]);
			for(int i1=0;i1<(s1[1].length());i1++)
			{
				
				if(s1[1].charAt(i1)=='0')
				{
					if(decode_temp.left==null)
					{
						Huffman_Node1 temp=new Huffman_Node1(-1,-1);
						decode_temp.left=temp;
					}
					decode_temp=decode_temp.left;
					
				}
				else if(s1[1].charAt(i1)=='1')
				{
					if(decode_temp.right==null)
					{
						Huffman_Node1 temp=new Huffman_Node1(-1,-1);
						decode_temp.right=temp;
					}
						decode_temp=decode_temp.right;
				}
			
			}
			decode_temp.data=Integer.parseInt(s1[0]);
			decode_temp=decode_root;
		}
	
		br1.close();
		fr1.close();
		
	}
	
	void generate_decode_msg(String encoded_file) throws IOException
	{
		System.out.println("generating decode msg");
		StringBuilder sb=new StringBuilder();
		//FileReader fr=new FileReader(encoded_file);
		BufferedInputStream br=new BufferedInputStream(new FileInputStream(encoded_file));
		int b;
		
		while((b=br.read()) != -1)
		{
			int shift=0x80;
			while((shift!=1) && ((b & shift)==0))
			{
				sb.append('0');
				shift=shift>>1;
			}
			sb.append(Integer.toBinaryString(b));
			//System.out.println("appedn");
		}
		br.close();
		//fr.close();
		//System.out.println("after while loop"+sb.substring(1,10));
		FileWriter fw=new FileWriter("decoded.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		Huffman_Node1 temp=decode_root;
		
		for(int i=0;i<(sb.length());i++)
		{
			//System.out.println("in for loop");
			
			if(sb.charAt(i)=='0')
			{
				temp=temp.left;
			}
			else
			{
				temp=temp.right;
			}
			if(temp.left==null && temp.right==null)
			{
				//System.out.println("tem data is "+temp.data);
				bw.write(Integer.toString(temp.data));
				bw.newLine();
				temp=decode_root;
			}
			
		}
		//System.out.println("after for loop");
		
		bw.close();
		fw.close();
		
	}
	public static void main(String args[]) throws IOException
	{
		long start_time=System.nanoTime();
		String encode_file_name=args[0];
		String code_table_file=args[1];
		decoder decoder=new decoder();
		decoder.generate_decode_tree(code_table_file);
		decoder.generate_decode_msg(encode_file_name);
		System.out.println("elapsed time for entire decoding process is "+(System.nanoTime()-start_time)*Math.pow(10, -6));
		System.out.println("Generated decoded msg");
	}
	
}
