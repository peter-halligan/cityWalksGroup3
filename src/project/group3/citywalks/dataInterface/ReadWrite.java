/*package project.group3.citywalks.dataInterface;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import project.group3.citywalks.objects.Walk;

public class ReadWrite 
{
    private ArrayList<Walk> read(String fileName)
    {
    	FileInputStream fis = getFileStreamPath(fileName).openFileInput(fileName);
    	ObjectInputStream is = new ObjectInputStream(fis);
    	ArrayList<Walk> walks = (ArrayList<Walk>) is.readObject();
    	is.close();
    	return walks;
    }
}
*/