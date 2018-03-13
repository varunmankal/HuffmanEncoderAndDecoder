import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;




public class Four_Way_Cache {
	
	 ArrayList<Huffman_Node> values;
	
	File f=new File("code_table.txt");
	FileWriter fw;
	BufferedWriter bw;	
	HashMap<Integer,String> hmap=new HashMap();
	
	 Four_Way_Cache(ArrayList<Huffman_Node> values) throws IOException
	{
		this.values = new ArrayList(values);
		//this.values=values;
		fw=new FileWriter(f);
		bw=new BufferedWriter(fw);
		
	}
	
	
	void Initialize()
	{
		for(int i1=(int) Math.floor((values.size()-1)/4)+2;i1>=3;i1--)
		{	//System.out.println("i1 is "+i1);
			heapify(i1);
		}
				
	}
	
	
	
	void heapify(int i)
	{
		//System.out.println("heapify method");
		int l1=4*(i-2);
		int l2=4*(i-2)+1;
		int l3=4*(i-2)+2;
		int l4=4*(i-2)+3;
		int smallest=i;
		//System.out.println("i is "+i);
		if(l1<values.size() && (values.get(l1).frequency < values.get(smallest).frequency))
		{
			smallest=l1;
		}
	
		if(l2<values.size() && (values.get(l2).frequency < values.get(smallest).frequency))
		{
			smallest=l2;
		}
		
		if(l3<values.size() &&  (values.get(l3).frequency < values.get(smallest).frequency))
		{
			smallest=l3;
		}
		
		if(l4<values.size() && (values.get(l4).frequency < values.get(smallest).frequency))
		{
			smallest=l4;
		}
		
		if(smallest!=i)
		{
			Huffman_Node temp=values.get(i);
			values.set(i,values.get(smallest));
			values.set(smallest,temp);
			heapify(smallest);
		}
	}
        
										
	void insert(Huffman_Node new_node)
	{
		//Huffman_Node temp=new Huffman_Node(new_node.data,new_node.frequency);
		values.add(new_node);
		int i=values.size()-1;
		while(((i/4)+2)>=3 && values.get((i/4)+2).frequency>values.get(i).frequency)
		{
			Huffman_Node temp=values.get(i);
			values.set(i,values.get((i/4)+2));
			values.set((i/4)+2,temp);
			i=(i/4)+2;
		}
	}
	
	Huffman_Node removemin()
	{
		if(values.size() < 4)
		{
			System.out.println("error overflow");
		}
		//System.out.println("size is "+values.size());
		Huffman_Node min=values.get(3);
		values.set(3, values.get((values.size())-1));
		//values.set((values.size())-1, null);
		values.remove(values.size()-1);
		heapify(3);
		return min;
	}

	public void inOrder(Huffman_Node root,String s) throws IOException {
		
		  if((root.left==null) && (root.right==null))
		  {
			bw.write(root.data+" "+s);
			bw.newLine();
			
			 hmap.put(root.data,s);
			  return;
		  }

		  inOrder(root.left,s+'0');
		  inOrder(root.right,s+'1');
		  return;
		  
		}
	
	public void generate_tree() throws IOException
	{
		while(values.size()!=4)
		{
			Huffman_Node temp1=removemin();
			Huffman_Node temp2=removemin();
			Huffman_Node new_node=new Huffman_Node(temp1.frequency+temp2.frequency,temp1,temp2);
			insert(new_node);
		}
		inOrder(values.get(3), "");
		bw.close();
		fw.close();
		System.out.println("key is "+values.get(3).data+" values is "+values.get(3).frequency);
	}
											
/*
	public static void main(String args[]) throws FileNotFoundException,IOException
	{
		Generate_Frequency obj=new Generate_Frequency();
		System.out.println("args[0] is "+ args[0]);
		obj.cal_frequency(args[0]);
		long[] freq_table=obj.get_freq_table();
		ArrayList<Huffman_Node> huffman_list=new ArrayList();
		Four_Way_Cache heap=new Four_Way_Cache();
		for(int i=0;i<freq_table.length;i++)
		{
			if(freq_table[i]!=0){
			Huffman_Node node=new Huffman_Node(i,freq_table[i]);
			//System.out.println("node val is "+node.frequency+"data is "+node.data
				
			
			Four_Way_Cache.values.add(node);
			}	
		}
		
		long start_time=System.nanoTime();
		heap.Initialize();

		while(Four_Way_Cache.values.size()!=1)
		{
			Huffman_Node temp1=heap.removemin();
			Huffman_Node temp2=heap.removemin();
			Huffman_Node new_node=new Huffman_Node(temp1.frequency+temp2.frequency,temp1,temp2);
			heap.insert(new_node);
		}
		System.out.println("elapsed time is"+(System.nanoTime()-start_time)*Math.pow(10, -6));

		heap.inOrder(Four_Way_Cache.values.get(0),"");
		FileReader fr=new FileReader(args[0]);
		BufferedReader br=new BufferedReader(fr);
		String input_data;
		StringBuilder sb=new StringBuilder();
		
		while((input_data=br.readLine())!=null)
		{

			sb.append(heap.hmap.get(Integer.parseInt(input_data)));	
		}
		
		
		int start=0;
		int end=0;
		

		
		BufferedOutputStream w=new BufferedOutputStream(new FileOutputStream("E:/UF/ADS/encoded.bin"));
		for(int i=0,len=sb.length();i<len;i+=8)
		{
			w.write((byte)Integer.parseInt(sb.substring(i, Math.min(i+8, len)),2));
		}
		w.close();
		
		
		
		
		
		
		
		
	heap.bw.close();
	heap.fw.close();
			
	}*/
}	

	



	
	
	
	

