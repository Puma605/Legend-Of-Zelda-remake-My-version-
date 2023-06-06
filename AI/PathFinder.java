package AI;

import java.util.ArrayList;

import entity.Entity;
import main.GamePanel;

public class PathFinder {
    GamePanel gp;
    Node node[][];
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, currentNode, goalNode;
    boolean goalReached = false;
    int steps = 0;
    

    public PathFinder(GamePanel gp){
        this.gp = gp;
        instantiateNode();
    }

    public void instantiateNode() {
        node = new Node[GamePanel.MaxWorldCol][GamePanel.MaxWorldRow];

        int col = 0;
        int row = 0;
        while (col < GamePanel.MaxWorldCol && row < GamePanel.MaxWorldRow) {
            node[col][row] = new Node(col, row);

            col++;
            if (col == GamePanel.MaxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes() {
        //reset nodes
        int col = 0;
        int row = 0;
        while (col < GamePanel.MaxWorldCol && row < GamePanel.MaxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if (col == GamePanel.MaxWorldCol) {
                col = 0;
                row++;
            }
        }
 
        openList.clear();
        pathList.clear();
        goalReached = false;
        steps = 0;

    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity){
        resetNodes();

        //start and goal node 
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);


        int col = 0;
        int row = 0;
        while (col < GamePanel.MaxWorldCol && row < GamePanel.MaxWorldRow) {
            //set solid nodes
            //check tiles
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if (gp.tileM.tile[tileNum].collision) {
                node[col][row].solid = true;
            }

            //check iTiles
            for (int i = 0; i < gp.iTile[1].length; i++) {
                if (gp.iTile[gp.currentMap][i] != null &&
                 gp.iTile[gp.currentMap][i].destructible) {
                    int itCol = gp.iTile[gp.currentMap][i].worldX/GamePanel.TileSize;
                    int itRow = gp.iTile[gp.currentMap][i].worldY/GamePanel.TileSize;
                    node[itCol][itRow].solid = true;
                }
            }

           //set cost
           getCost(node[col][row]);

           col++;
           if (col == GamePanel.MaxWorldCol) {
            col = 0;
            row++;
           }

        }
    }
    public void getCost(Node node) {
        //get g cost
        int xDistance = Math.abs(node.col-startNode.col);
        int yDistance = Math.abs(node.row-startNode.row);
        node.gCost = xDistance+ yDistance;

        //get h cost
        xDistance = Math.abs(node.col-goalNode.col);
        yDistance = Math.abs(node.row-goalNode.row);
        node.hCost = xDistance+ yDistance;

        //get f cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while(!goalReached && steps < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            //open the up node
            if (row-1 >=0) 
            openNode(node[col][row-1]); 

            //open the left node
            if (col-1 >= 0) 
            openNode(node[col-1][row]);  
            
            //open the right node
            if (col+1 < GamePanel.MaxWorldCol) 
            openNode(node[col+1][row]);    
            
            
            //open the down node
            if (row+1 < GamePanel.MaxWorldRow) 
            openNode(node[col][row+1]);

            //find best node
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                //check the f cost
                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }
                //if f cost is equal tan checks g cost
                else if (openList.get(i).fCost == bestNodeFCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            //if there is no open node in the open list
            if (openList.size() == 0){
                break;
            }
            

            //after the loop is done we get the next best node
            currentNode = openList.get(bestNodeIndex);
            
            if (currentNode == goalNode){
                goalReached = true;
              //  steps = 0;
                trackPath();
            }
            steps++;
            
        } 
        return goalReached;
    }

    public void openNode(Node node){
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackPath() {
        //backtrack path
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
