import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dots
{
	public static class CellMap						//存活数据地图
	{
		int rowCellNum;								//细胞个数
		int colCellNum;
		Boolean cell[];								//地图大小
		Boolean cellNext[];
		int cellMin = 2;							//存活时相邻细胞数最小值、最大值
		int cellMax = 3;
		int cellRMin = 3;							//重生时细胞数最小值、最大值
		int cellRMax = 3;	
		Integer year;								//周期数
		public CellMap(int r, int c)							//初始化
		{
			rowCellNum = r;
			colCellNum = c;
			cell = new Boolean[rowCellNum*colCellNum];
			cellNext = new Boolean[rowCellNum*colCellNum];
			year=0;
			for(int i=0;i<rowCellNum*colCellNum;i++)
			{
				cell[i]=false;
				cellNext[i]=false;
			}
		}
		public Boolean getCell(int x, int y)		//坐标获取细胞信息
		{
			return cell[x+y*colCellNum];
		}
		public Boolean getCell(int a)				//序号获取细胞信息
		{
			return cell[a];
		}
		public void randomize()						//随机化
		{
			for(int i=0;i<rowCellNum*colCellNum;i++)
			{
				if(Math.random()>0.5)	{cell[i]=true;}
				else 					{cell[i]=false;}
			}
			year=0;
		}
		public void clear()
		{
			for(int i=0;i<rowCellNum*colCellNum;i++)
			{
				cell[i]=false;
			}
			year=0;
		}
		public int count(int x, int y)				//相邻细胞个数
		{
			int c=0;
			if(x>0 				&& getCell(x-1,y))	c++;
			if(x<colCellNum-1	&& getCell(x+1,y))	c++;
			if(y>0 				&& getCell(x,y-1))	c++;
			if(y<rowCellNum-1	&& getCell(x,y+1))	c++;
			if(x>0 				&& y>0 				&& getCell(x-1,y-1))	c++;
			if(x>0 				&& y<rowCellNum-1	&& getCell(x-1,y+1))	c++;
			if(x<colCellNum-1	&& y>0 				&& getCell(x+1,y-1))	c++;
			if(x<colCellNum-1	&& y<rowCellNum-1	&& getCell(x+1,y+1))	c++;
			return c;
		}
		public int count2(int x, int y)				//相邻细胞个数（边界循环）
		{
			int c=0;
			if(x>0 				&& getCell(x-1,y))	c++;
			if(x<colCellNum-1	&& getCell(x+1,y))	c++;
			if(y>0 				&& getCell(x,y-1))	c++;
			if(y<rowCellNum-1	&& getCell(x,y+1))	c++;
			if(x>0 				&& y>0 				&& getCell(x-1,y-1))	c++;
			if(x>0 				&& y<rowCellNum-1	&& getCell(x-1,y+1))	c++;
			if(x<colCellNum-1	&& y>0 				&& getCell(x+1,y-1))	c++;
			if(x<colCellNum-1	&& y<rowCellNum-1	&& getCell(x+1,y+1))	c++;
			if(x==0 			&& getCell(colCellNum-1,y))	c++;
			if(x==colCellNum-1 	&& getCell(0,y))			c++;
			if(y==0 			&& getCell(x,rowCellNum-1))	c++;
			if(y==rowCellNum-1 	&& getCell(x,0))			c++;
			if(x==0 			&& y>0 				&& getCell(colCellNum-1,y-1))	c++;
			if(x==colCellNum-1	&& y>0				&& getCell(0,y-1))				c++;
			if(x==0 			&& y<rowCellNum-1	&& getCell(colCellNum-1,y+1))	c++;
			if(x==colCellNum-1	&& y<rowCellNum-1	&& getCell(0,y+1))				c++;
			if(x>0 				&& y==0 			&& getCell(x-1,rowCellNum-1))	c++;
			if(x>0				&& y==rowCellNum-1	&& getCell(x-1,0))				c++;
			if(x<colCellNum-1 	&& y==0				&& getCell(x+1,rowCellNum-1))	c++;
			if(x<colCellNum-1	&& y==rowCellNum-1	&& getCell(x+1,0))				c++;
			if(x==0 			&& y==0 			&& getCell(colCellNum-1,rowCellNum-1))	c++;
			if(x==0 			&& y==rowCellNum-1	&& getCell(colCellNum-1,0))				c++;
			if(x==colCellNum-1	&& y==0 			&& getCell(0,rowCellNum-1))				c++;
			if(x==colCellNum-1	&& y==rowCellNum-1	&& getCell(0,0))						c++;
			return c;
		}
		public int count(int a)
		{
			return count(a%colCellNum,a/colCellNum);
		}
		public int count2(int a)
		{
			return count2(a%colCellNum,a/colCellNum);
		}
		public void next()							//下一轮
		{
			for(int i=0;i<rowCellNum*colCellNum;i++)
			{
				if(getCell(i) && count(i)>=cellMin && count(i)<=cellMax)			{cellNext[i]=true;}
				else if(!getCell(i) && count(i)>=cellRMin && count(i)<=cellRMax)	{cellNext[i]=true;}
				else 																{cellNext[i]=false;}
			}
			for(int i=0;i<rowCellNum*colCellNum;i++)
			{
				cell[i]=cellNext[i];
			}
			year++;
		}
		public void next2()							//下一轮（边界循环）
		{
			for(int i=0;i<rowCellNum*colCellNum;i++)
			{
				if(getCell(i) && count2(i)>=cellMin && count2(i)<=cellMax)			{cellNext[i]=true;}
				else if(!getCell(i) && count2(i)>=cellRMin && count2(i)<=cellRMax)	{cellNext[i]=true;}
				else 																{cellNext[i]=false;}
			}
			for(int i=0;i<rowCellNum*colCellNum;i++)
			{
				cell[i]=cellNext[i];
			}
			year++;
		}
		public void reverse(int a)					//反转细胞
		{
			cell[a] = !cell[a];
		}
		public void setRule(int min, int max, int rmin, int rmax)
		{
			cellMin = min;
			cellMax = max;
			cellRMin = rmin;
			cellRMax = rmax;
		}
	}

	public static class JCell extends JPanel		//细胞格子显示
	{
		int a;
		public JCell(int num)
		{
			a=num;
		}
		public void setColor(CellMap m)
		{
			if(m.getCell(a))	{this.setBackground(new Color(0,0,0));}
			else 				{this.setBackground(new Color(255,255,255));}
		}
	}

	public static class autoRun extends Thread
	{
		@Override
		public void run()
		{
			//
		}
	}

	private static void BoxGUI(int colNum, int rowNum)					//窗体
	{
		//int colNum = 25;							//x列数
		//int rowNum = 25;							//y行数
		JFrame f = new JFrame("CPK细胞自动机");
		f.setLayout(new BorderLayout());
		f.setSize((colNum>=23)?colNum*20+50:500, rowNum*20+100);
		f.setLocation(300, 200);
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel dotsPanel = new JPanel();		//图形区域
		JPanel configPanel = new JPanel();		//控制区域

		CellMap map = new CellMap(rowNum, colNum);		//细胞地图
		map.randomize();
												////////////////图形区域开始
		JPanel dotsArea = new JPanel();

		dotsArea.setLayout(new GridLayout(rowNum,colNum));
		dotsArea.setPreferredSize(new Dimension(colNum*20, rowNum*20));
		JCell dotsCell[] = new JCell[rowNum*colNum];
		for(int i=0;i<rowNum*colNum;i++)				//循环创建细胞
		{
			dotsCell[i] = new JCell(i);
			dotsCell[i].setColor(map);
			dotsArea.add(dotsCell[i]);
		}
		dotsArea.addMouseListener(new MouseAdapter() {
            public void mousePressed(final MouseEvent e) {
                Integer clickCell = new Integer((e.getX()/20) + (e.getY()/20)*colNum);
                map.reverse(clickCell);
                dotsCell[clickCell].setColor(map);
            }
        });
		dotsPanel.add(dotsArea);

												////////////////控制区域开始
		JButton clrBtn = new JButton("清空");
		JButton randBtn = new JButton("随机化");
		JButton nextBtn = new JButton("下一步");
		JTextField yearCount = new JTextField(3);
		yearCount.setEditable(false);
		yearCount.setText(map.year.toString());
		JButton autoBtn = new JButton("自动演化");
		JCheckBox borderButton =new JCheckBox("边界循环");
		JButton ruleBtn = new JButton("演化参数");

		clrBtn.addActionListener(e->			//清空
		{
			map.clear();
			for(int i=0;i<rowNum*colNum;i++)
			{
				dotsCell[i].setColor(map);
			}
			yearCount.setText(map.year.toString());
		});

		randBtn.addActionListener(e->			//随机化
		{
			map.randomize();
			for(int i=0;i<rowNum*colNum;i++)
			{
				dotsCell[i].setColor(map);
			}
			yearCount.setText(map.year.toString());
		});

		nextBtn.addActionListener(e->			//下一步
		{
			if(borderButton.isSelected())	map.next2();
			else 							map.next();
			for(int i=0;i<rowNum*colNum;i++)
			{
				dotsCell[i].setColor(map);
			}
			yearCount.setText(map.year.toString());
		});

		autoBtn.addActionListener(e->			//自动演化
		{
			JDialog d = new JDialog(f, "dbq");
			d.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			d.setSize(250,80);
			d.setLocation(400, 400);
			d.setVisible(true);
			JLabel labelError = new JLabel("这个功能还是不会弄",JLabel.CENTER);
			d.add(labelError);
		});

		ruleBtn.addActionListener(e->			//规则控制
		{
			JDialog d = new JDialog(f, "设置参数");
			d.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			d.setSize(200,200);
			d.setLocation(300, 200);
			d.setVisible(true);
			d.setLayout(new GridLayout(5,1));
			JTextField minText = new JTextField();
			JTextField maxText = new JTextField();
			JTextField rminText = new JTextField();
			JTextField rmaxText = new JTextField();
			JButton setBtn = new JButton("确定");
			setBtn.addActionListener(e2->			//自动演化
			{
				map.setRule(Integer.parseInt(minText.getText()),Integer.parseInt(maxText.getText()),
					Integer.parseInt(rminText.getText()),Integer.parseInt(rmaxText.getText()));
			});
			d.add(minText);
			d.add(maxText);
			d.add(rminText);
			d.add(rmaxText);
			d.add(setBtn);
		});

		configPanel.add(clrBtn);
		configPanel.add(randBtn);
		configPanel.add(nextBtn);
		configPanel.add(yearCount);
		configPanel.add(autoBtn);
		configPanel.add(borderButton);
		configPanel.add(ruleBtn);

												////////////////
		f.add(dotsPanel,BorderLayout.CENTER);
		f.add(configPanel,BorderLayout.SOUTH);
	}

	private static void Launcher()
	{
		JFrame f = new JFrame("启动器");
		f.setLayout(new FlowLayout(FlowLayout.CENTER));
		f.setSize(300, 160);
		f.setLocation(300, 200);
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel rowSetting = new JPanel();
		JPanel colSetting = new JPanel();
		JPanel buttonPanel = new JPanel();

		JLabel rowSetLabel = new JLabel("行数：");
		JLabel colSetLabel = new JLabel("列数：");
		JTextField rowSetText = new JTextField("25", 10);
		JTextField colSetText = new JTextField("25", 10);
		JButton buttonOK = new JButton("　　确定　　");

		buttonOK.addActionListener(event -> 
			{
				try
				{
					Integer rowNum = Integer.parseInt(rowSetText.getText());
					Integer colNum = Integer.parseInt(colSetText.getText());
					f.dispose();
					BoxGUI(rowNum, colNum);
				}
				catch(NumberFormatException e){
					rowSetText.setText("你这填的啥玩意");
					colSetText.setText("你这填的啥玩意");
				}
			});

		rowSetting.add(rowSetLabel);
		rowSetting.add(rowSetText);
		colSetting.add(colSetLabel);
		colSetting.add(colSetText);
		buttonPanel.add(buttonOK);

		f.add(rowSetting);
		f.add(colSetting);
		f.add(buttonPanel);

	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(Dots::Launcher);
	}
}