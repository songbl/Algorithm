package sample.bfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BFSClass {

    public static void main(String[] args){
        //初始化先建立起各个节点信息，以及对应的直接子节点列表
        HashMap<String,String[]> map = new HashMap<>();
        map.put("A", new String[] {"B","C"});
        map.put("B", new String[] {"E","C","F"});
        map.put("C", new String[] {"D","F"});
        map.put("D", new String[] {"E"});
        map.put("E", new String[] {"H"});
        map.put("F", new String[] {"E","G"});
        map.put("G", new String[] {"H"});
        map.put("H", new String[] {});
        //获取从A到H的最短路径节点链表
        Node target = findTarget("A","F",map);
        //打印出最短路径的各个节点信息
        printSearPath(target);

    }

    /**
     * 打印出到达节点target所经过的各个节点信息
     * @param target
     */
    static void printSearPath(Node target) {
        if (target != null) {
            System.out.print("找到了目标节点:" + target.id + "\n");

            List<Node> searchPath = new ArrayList<Node>();
            searchPath.add(target);
            Node node = target.parent;
            while(node!=null) {
                searchPath.add(node);
                node = node.parent;
            }
            String path = "";
            for(int i=searchPath.size()-1;i>=0;i--) {
                path += searchPath.get(i).id;
                if(i!=0) {
                    path += "-->";
                }
            }
            System.out.print("步数最短："+path);
        } else {
            System.out.print("未找到了目标节点");
        }
    }

    /**
     * 从指定的开始节点 startId ，查询到目标节点targetId 的最短路径
     * @param startId
     * @param stopId
     * @param map
     * @return
     *E peek();获取但不移除队列头部元素，如果队列为空，返回null；
     */
    static Node findTarget(String startId,String stopId,HashMap<String,String[]> map) {
        //用于存储查询过的id
        List<String> hasSearchList = new ArrayList<String>();
        //要查询
        LinkedList<Node> queue = new LinkedList<Node>();
        //将对象e插入队列尾部，成功返回true，失败（没有空间）返回false；
        queue.offer(new Node(startId,null));
        while(!queue.isEmpty()) {
            //获取并移除队列头部元素，如果队列为空，返回null；
            Node node = queue.poll();

            if(hasSearchList.contains(node.id)) {
                //跳过已经搜索过的，避免重复或者死循环
                continue;
            }
            System.out.print("判断节点:" + node.id +"\n");
            //最后一个节点，在队列顶部。就结束了
            if (stopId.equals(node.id)) {
                return node;
            }
            hasSearchList.add(node.id);
            //将当前节点的之间关系节点，放进查询队列中
            if (map.get(node.id) != null && map.get(node.id).length > 0) {
                for (String childId : map.get(node.id)) {
                    queue.offer(new Node(childId,node));
                }
            }
        }

        return null;
    }

    /**
     * 节点对象
     * @author Administrator
     *
     */
    static class Node {
        //节点唯一id
        public String id;
        //该节点的直接父节点
        public Node parent;

        public Node(String id, Node parent) {
            this.id = id;
            this.parent = parent;
        }
    }
}
