import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Huffman_Node
{
	int data;
	long frequency;
	Huffman_Node left;
	Huffman_Node right;
	Huffman_Node(int key,long freq_value)
	{
			data=key;
			frequency=freq_value;
			left=null;
			right=null;
	}
	
	Huffman_Node(long freq_value,Huffman_Node l,Huffman_Node r)
	{
		frequency=freq_value;
		left=l;
		right=r;
	}
	
}


public class encoder {
	
	static long freq[]; //array to store keys and frequencies
	static ArrayList<Huffman_Node> values;//list to store huffman nodes
	
	//constructor for initializing, calculating freq's and initializing values list
	encoder() throws NumberFormatException, IOException
	{
		freq=new long[1000000];
		values=new ArrayList<Huffman_Node>();
	}
	
	public void generate_freq_table(String filename) throws NumberFormatException, IOException
	{
		FileReader fd=new FileReader(filename);//opening input file to read
		BufferedReader in=new BufferedReader(fd);
		String k;
		
		while((k=in.readLine())!=null)//reading line by line until end of file is reached
		{
			++freq[Integer.parseInt(k)];//updating the frequencies
				
		}
		for(int i=0;i<3;i++)
		{
			Huffman_Node dummy=new Huffman_Node(0,0);
			values.add(dummy);
					
		}
		for(int i=0;i<freq.length;i++)//creating huffman nodes and storing in the list
		{	
			if(freq[i]!=0)
			{	
			
				Huffman_Node node=new Huffman_Node(i,freq[i]);
			
				values.add(node);
			}
			
		}
		in.close();	//closing file streams
		fd.close();
	}
	
	public void writing_encoded_msg(BufferedOutputStream bos,String s, String filename) throws NumberFormatException, IOException
	{
		for(int i=0,len=s.length();i<len;i+=8)
		{
			bos.write((byte)Integer.parseInt(s.substring(i, i+8),2));
		}
	}
	
	
public static void main(String args[]) throws IOException
{
	
	long start_time=System.nanoTime();
	String filename=args[0];//input data file
	
	encoder encoder_obj=new encoder();
	encoder_obj.generate_freq_table(filename);
	System.out.println("time to build frequency table is"+(System.nanoTime()-start_time)*Math.pow(10, -6));
	
	//passing huffman list to binary heap
	//Binary_Heap binaryheap=new Binary_Heap(encoder.values);
	long start_built_tree_time=System.nanoTime();
	
	Four_Way_Cache binaryheap=new Four_Way_Cache(encoder.values); 
	
	
	
	binaryheap.Initialize();
	
	binaryheap.generate_tree();
	
	System.out.println("time to huffman tree is"+(System.nanoTime()-(start_built_tree_time))*Math.pow(10, -6));
	FileReader fr=new FileReader(filename);
	BufferedReader br=new BufferedReader(fr);
	String input_data;
	StringBuilder sb=new StringBuilder();
	
	String filename1="encoded.bin";
	BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(filename1));
	//int count=0;
	while((input_data=br.readLine())!=null)
	{
		//if(sb.length()>10000008 && (sb.length()%8==0)){
		sb.append(binaryheap.hmap.get(Integer.parseInt(input_data)));	
		//count=0;
		//encoder_obj.writing_encoded_msg(bos, sb.toString(), filename1);
		//sb=new StringBuilder();

		if(sb.length() > 1000000)
		{
			int len = sb.length();
			int diff=len-(len%8);
			String sub="";
			if(diff!=len)
			{
				sub=sb.substring(diff, len);
				sb.delete(diff, len);
			}
			encoder_obj.writing_encoded_msg(bos, sb.toString(), filename1);
			sb=new StringBuilder();
			sb.append(sub);
		}
	}
	
	
	if(sb.length()!=0)
	{
		encoder_obj.writing_encoded_msg(bos, sb.toString(), filename1);
	}
	
//	for(int i=0,len=sb.length();i<len;i+=8)
//	{
//		bos.write((byte)Integer.parseInt(sb.substring(i, Math.min(i+8, len)),2));
//	}
	
	br.close();
	fr.close();
		
	
	
	bos.close();
	
	System.out.println("time taken to generate encoded txt is "+(System.nanoTime()-start_time)*Math.pow(10,-6));
	
	
}
	
}
