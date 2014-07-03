package com.example.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.comparator.SizeFileComparator;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ArrayList<String> imageList = new ArrayList<String>();
		ArrayList<String> audioList = new ArrayList<String>();
		ArrayList<String> videoList = new ArrayList<String>();
		ArrayList<String> otherList = new ArrayList<String>();

		String root_sd = Environment.getExternalStorageDirectory().toString();

		ArrayList<File> listOfFiles = new ArrayList<File>();
		returnFiles(root_sd, listOfFiles);
		File[] filesArray = new File[listOfFiles.size()];
		Arrays.sort(listOfFiles.toArray(filesArray),
				SizeFileComparator.SIZE_COMPARATOR);
		// List<File> sortedList = sort(listOfFiles);
		for (File eachfile : filesArray)
		{
			if (eachfile.isFile())
			{
				if (isImage(eachfile.getName()))
				{
					imageList.add(eachfile.getName() + " " + eachfile.length());
				}
				else if (isAudio(eachfile.getName()))
				{
					audioList.add(eachfile.getName() + " " + eachfile.length());
				}
				else if (isVideo(eachfile.getName()))
				{
					videoList.add(eachfile.getName() + " " + eachfile.length());
				}
				else
				{
					otherList.add(eachfile.getName() + " " + eachfile.length());
				}
			}
		}
		Collections.reverse(imageList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, imageList);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	}

	private List<File> sort(ArrayList<File> listOfFiles)
	{
		List<File> sortedList = new ArrayList<File>();
		for (int i = 0; i < listOfFiles.size(); i++)
		{
			for (int j = i + 1; j < listOfFiles.size(); j++)
			{
				if (listOfFiles.get(i).length() > listOfFiles.get(j).length())
				{
					int temp = i;
					i = j;
					j = temp;
					sortedList.add(listOfFiles.get(i));
				}
				else
				{
					sortedList.add(listOfFiles.get(j));
				}
			}
		}

		return sortedList;
	}

	private boolean isImage(String fileName)
	{
		String[] imageExtensions =
		{ "gif", "png", "jpg", "bmp" };
		for (String extension : imageExtensions)
		{
			if (fileName.endsWith("." + extension))
			{
				return true;
			}
		}
		return false;

	}

	private boolean isAudio(String fileName)
	{
		String[] imageExtensions =
		{ "mp3" };
		for (String extension : imageExtensions)
		{
			if (fileName.endsWith("." + extension))
			{
				return true;
			}
		}
		return false;

	}

	private boolean isVideo(String fileName)
	{

		String[] imageExtensions =
		{ "mkv", "mp4", "avi", "3gp" };
		for (String extension : imageExtensions)
		{
			if (fileName.endsWith("." + extension))
			{
				return true;
			}
		}
		return false;

	}

	private void returnFiles(String folderName, ArrayList<File> listOfFiles)
	{
		File file = new File(folderName);
		File list[] = file.listFiles();
		for (File eachfile : list)
		{
			if (eachfile.isFile())
			{
				listOfFiles.add(eachfile);
			}
			else if (eachfile.isDirectory())
			{
				returnFiles(eachfile.getAbsolutePath(), listOfFiles);
			}
		}
	}
}
