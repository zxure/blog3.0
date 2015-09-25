package zx.blog.article.utils;

import java.io.IOException;

public class Test {
	public static void main(String args[]) throws Exception{
		String content = "<p align=\"center\">dfas<p>5</p>xx34<img alt=\"甘肃南部舟曲县今晨突发特大泥石流灾害\" src=\"http://img1.gtimg.com/news/pics/hv1/107/108/594/38652497.jpg\" />"+
	"甘肃舟曲县今晨突发特大泥石流灾害，车辆冲入水中</p><p style=\"text-indent:2em;\">8月7日22时许，甘南州舟曲县突降强降雨，县城北面的罗家峪、三眼峪泥石流下泄，由北向南冲向县城，造成沿河房屋被冲毁，泥石流阻断白龙江、形成堰塞湖。据初步调查，截至8月8日11时，舟曲县特大洪灾造成县城由北向南5公里长、500 米宽区域被夷为平地（约250万平米），受灾人数约2万人，遇难80多人，受伤70多人，失踪人数正在统计当中。目前，县城洪水水位较早晨6时下降 40公分，但由于白龙江上游迭部段早晨突降暴雨，白龙江洪峰到达舟曲后，水位仍有升高趋势。</p><p style=\"text-indent:2em;\">目前，省委书记、省人大常委会主任陆浩，省委副书记、代省长刘伟平，副省长、省抗旱防汛指挥部总指挥泽巴足已分别带领有关部门负责人赶到灾区现场，正在指导开展抗洪救灾工作。同时，省政府已启动了I级防汛抗洪应急响应和自然灾害救助Il级响应，全力开展抗洪抢险救灾工作。</p><p style=\"text-indent:2em;\">经省委、省政府商请，省军区报请兰州军区调动的驻临洮某部600名官兵、驻宁夏中卫某部700 名官兵，解放军第一医院、解放军临夏第七医院两个医疗队已出发赶赴灾区。8日6时30分，2架直升机载爆破专家紧急飞往灾区；兰州军区某部2000名官兵正在赶赴灾区途中。</p><p style=\"text-indent:2em;\">按照省委、省政府主要领导的要求，省国土资源厅由一名副厅长带队的地质灾害应急调查队已于 4时35分赶赴现场。省卫生厅组建的由省人民医院19名医护人员组成的医疗队，省疾病预防控制中心4名防疫人员组成的防疫队，省卫生厅卫生监督所6名卫生监督人员组成的卫生监督队，携带药品和设备，由省卫生厅厅长带队已于8时30分赶赴灾区。甘南州卫生局已于4时30分派遣由45人组成的医疗队赶赴受灾现场。省交通厅已调集甘南公路总段、陇南公路总段和参与陇南灾后重建的交通队伍和武警交通六支队紧急赶赴受灾路段，其中携带大型机械的甘南公路总段和武警交通六支队最早投入到抢通工作中。测绘、电力、通信等部门也分别组织专业人员、携带必要的装备连夜赶赴灾区抢险救灾。" +
	"</p>";
		
		/*String html = readByMaxLen(content,1000);
        Parser parser = Parser.createParser(new String(html.getBytes(),"8859_1"),"");
        PrototypicalNodeFactory factory = new PrototypicalNodeFactory ();
        parser.setNodeFactory(factory);
        @SuppressWarnings("serial")
		NodeList nodelist = parser.extractAllNodesThatMatch(new NodeFilter(){
            public boolean accept(Node node)
            {
                if(node instanceof CompositeTag)
                    return true;
                return false;
            }
        });
        
        String str = "";
        String tmp = "";
        for (int i = 0; i < nodelist.size(); i++)    {
            CompositeTag testTag = (CompositeTag)nodelist.elementAt(i);        
            if (testTag.getParent() == null) {
                tmp = new String(testTag.toHtml().getBytes("8859_1"));
                str += tmp + "/n";
            }
        }*/
		
		String str = ArticleBriefTools.getBrief(content, 230);
		
        System.out.println(str);
	}
	
	public static String readByMaxLen(String content,int length) throws IOException {        
        int pos = 0,len = 0,count = 0;
        String s = "";
        StringBuffer sb = new StringBuffer();
        while(true)
        {
            if(count > length)
                break;
             if(pos >= content.length())
            	 return sb.toString();
             s = content.substring(pos, pos+1);
             if(s.equals("<"))
             {
                 len = content.indexOf(">", pos)-pos;
                 for(int i=0;i<len;i++)
                 {
                     s = content.substring(pos+i, pos+i+1);
                     sb.append(s);
                 }
                 pos += len;
             }
             else
             {
                 if(count <= length)
                 {
                      if(s.equals(">"))
                        {
                            sb.append(s);
                            pos++;
                        }
                        else{
                     sb.append(s);
                     count++;
                     pos++;
                        }
                 }
             }
        }
        return sb.toString();
    }
	
}
