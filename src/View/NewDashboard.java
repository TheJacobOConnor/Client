/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Backend.Account;
import Backend.Client.Client;
import Backend.Client.ClientHandler;
import Backend.Client.ServerMessageListener;
import Backend.Client.ServerMessageHandler;
import Backend.Client.ServerRequestHandler;
import Backend.CloneSystem.CloningMachineHandler;
import Backend.CloneSystem.Cutting;
import Backend.CloneSystem.CuttingHandler;
import Backend.Dashboard.DashboardHandler;
import Backend.GrowRoomData.GrowModuleHandler;
import Backend.GrowRoomData.GrowRoom;
import Backend.GrowRoomData.GrowRoomController;
import Backend.Indoor.IndoorHandler;
import Backend.Indoor.Map.IndoorMap;
import Backend.Inventory.InventoryController;
import Backend.JacobUtility;
import Backend.Indoor.IndoorPlantListHandler;
import Backend.MapHandler;
import Backend.MyTools.ReadDataThread;
import Backend.PlantData.Plant;
import Backend.PlantData.PlantCreationHandler;
import Backend.Seed.SeedBankHandler;
import View.customcomponents.customNotifications.NotificationPanel;
import View.customcomponents.scrollbar.CustomScrollBar;
import java.awt.Color;
import java.awt.Cursor;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import raven.chart.CurveLineChart;
import raven.chart.ModelChart;




/**
 *
 * @author jakeo
 */
public class NewDashboard extends javax.swing.JFrame {

    int selectedIndex = -1;
    String enteredPassword = "";
    boolean loggedIn = false;
    boolean wantingToExit = false;
    boolean hoverIn = false;
    Account user = new Account();
    String selectedSlot = "";
    

    //SeedBank global vars
    int addPackSeedQuantity = 0;
    int packQuantityTemp = 0;
    String addPackReturnString;
    //Seedbank global vars
    
    //Indoor Global Variables
    int selectedIndoorPlantIndex = -1;
    String selectedGrowRoom = "";
    ArrayList<Plant> indoorPlantList = new ArrayList();
    //GrowRoom currentRoom = new GrowRoom("0");
    //
    
    //Client vars
    ArrayList<String> streamList = new ArrayList<>();
    Socket socket = null;
    boolean phenoLoaded = false;
    JLabel previousSelectedPin = new JLabel();
    Client client;
    
    boolean awaitingData = false;
    //Client vars
    
    //Notifications
    
    
   
    
    //Cutting Variables
    ArrayList<Cutting> cuttingList = new ArrayList<>();
    
    
    public NewDashboard() throws IOException {
        initComponents();
        this.dispose();
        
        this.setUndecorated(true);
        this.setVisible(true);
        
        settingsLabel.setName("settingsLabel");
        
        DashboardHandler.dashboardStartup(this.getPanelList(), this.getLabelList(), this.getSideLabels());
        
        
        
        
        
        while(!loggedIn)
        {
            
            this.setVisible(false);
            
            Login login = new Login(this, true);
            this.dispose();
            this.setUndecorated(true);
            login.setVisible(true);
            enteredPassword = login.getEnteredPassword();
            user = login.getAccount();
            loggedIn = login.getLoginStatus();
            wantingToExit = login.getExit();
            
            if(wantingToExit == true)
            {
                System.exit(0);
            }
            
            
        }
        System.out.println("Here");
        socket = new Socket("localhost", 43512);
        client = new Client(socket, user.getName(), enteredPassword);
        ReadDataThread dt = new ReadDataThread(client);
        ClientHandler.setClient(client);
        //dt.start();
        ServerMessageHandler serverMessageHandler;
        try 
        {
            serverMessageHandler = new ServerMessageHandler();
            ServerMessageListener serverMessageListener = new ServerMessageListener(client, serverMessageHandler);
            serverMessageListener.listen();
        } 
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(NewDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (NoSuchMethodException ex)
        {
            Logger.getLogger(NewDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        //this.currentRoom = new GrowRoom("97");
        //currentRoom.getGrowRoomController().setPanels(this.getIndoorPanelList());
        //IndoorHandler.setGrowRoom(currentRoom);
        //client.getStreamList(),this.IndoorPlantSelectionPanel, this.IndoorRoomDetailsPanel, this.IndoorCalendarPanel
        graphicSetup();
        setupSeedBank();
        setupPlantCreation();
        
        
    }
    
    
    private void setupSeedBank()
    {
  
        SeedBankHandler.setAddPackTextFields(getAddPackTextFields());
        SeedBankHandler.setDescriptionArea(this.addPackDescriptionArea);
        
        
    }
    
    private ArrayList<JTextField> getAddPackTextFields()
    {
        ArrayList<JTextField> textFields = new ArrayList<>();
        
        textFields.add(this.addPackCultivarField);
        textFields.add(this.addPackBreederField);
        textFields.add(this.addPackStockField);
        textFields.add(this.addPackReleaseField);
        textFields.add(this.addPackLineageField);
        textFields.add(this.addPackSeedTypeField);
        textFields.add(this.addPackFloweringTimeField);
        textFields.add(this.addPackTerpeneProfileField);
        textFields.add(this.addPackSizeField);
        textFields.add(this.addPackYieldField);
        
        return textFields;
       
    }
    
    
    
    private void graphicSetup()
    {
        
        
        //Indoor temp
        ArrayList<JPanel> growRoomPanelList = new ArrayList();
        growRoomPanelList.add(this.SelectedGrowRoomPanel);
        growRoomPanelList.add(this.IndoorModulePanel);
        GrowRoomController.setGrowRoomPanels(growRoomPanelList);
        
        
        //Notifaction Demo
        
        
        
        //this.NotificationsPanel.add(notificationPanel);
       
        
        //scrollbar
        /*CustomScrollBar cs = new CustomScrollBar();
        indoorPlantListScrollPane.setVerticalScrollBar(cs);
        
        CustomScrollBar ds = new CustomScrollBar();
        //indoorPlantListScrollPane.setVerticalScrollBar(ds);*/
        
     
        
        
        
        
        
        
        //Plant list demo
        JLabel testbglabel = new JLabel();
        testbglabel.setBounds(0, 0, this.growRoomOverviewPanel.getWidth(), this.growRoomOverviewPanel.getHeight());
        Icon icon = new ImageIcon("src/Data/Images/Indoor/Panels/growroomSelectionPanel.png");
        testbglabel.setIcon(icon);
        
        
        CurveLineChart clc = new CurveLineChart();
        //clc.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        clc.setTitle("Plant Growth");
       
        clc.setBounds(10, 585, 725, 300);
        clc.addLegend("Veg", Color.decode("#014e00"), Color.green);
        clc.addLegend("Flower", Color.decode("#e65c00"), Color.decode("#F9D423"));
        clc.clear();
        clc.addData(new ModelChart("Week 1", new double[]{4,0,9}));
        clc.addData(new ModelChart("Week 2", new double[]{9,-1,3}));
        clc.addData(new ModelChart("Week 3", new double[]{12,-1,1}));
        /*clc.addData(new ModelChart("Week 4", new double[]{14,28,4}));
        clc.addData(new ModelChart("Week 5", new double[]{26,21,6}));
        clc.addData(new ModelChart("Week 6", new double[]{48,20,9}));
        clc.addData(new ModelChart("Week 7", new double[]{32,17,9}));
        clc.addData(new ModelChart("Week 8", new double[]{48,18,9}));
        clc.addData(new ModelChart("Week 9", new double[]{27,10,9}));
        clc.addData(new ModelChart("Week 10", new double[]{36,42,9}));
        clc.addData(new ModelChart("Week 11", new double[]{24,48,9}));
        clc.addData(new ModelChart("Week 12", new double[]{21,50,9}));*/
        clc.setFillColor(true);
        clc.start();
        this.growRoomOverviewPanel.add(clc);
        //this.testPanel.add(testbglabel);
        assignSeedBankPanels();
        assignInventoryPanels();
        this.setBounds(0, 0, 1260, 915);
        this.setLocationRelativeTo(null);
        GrowModuleHandler.setGrowModulePanel(this.IndoorModulePanel);
        IndoorPlantListHandler.setListPanel(this.customIndoorListPanel);
        //IndoorPlantListHandler.panelSetupTest(new Plant("Weekend @ Gary's #1", "1290", "Weekend @ Gary's", "29"));
        Plant p = new Plant("Weekend @ Gary's #1", "1290", "Weekend @ Gary's", "29");
        Plant p1 = new Plant("Goon Berries #2", "1290", "Weekend @ Gary's", "29");
        Plant p2 = new Plant("Space Juice #3", "1290", "Weekend @ Gary's", "29");
        Plant p3 = new Plant("Bahama Mama S1 # 2", "1290", "Weekend @ Gary's", "29");
        IndoorPlantListHandler.addPlantToList(p);
        IndoorPlantListHandler.addPlantToList(p1);
        IndoorPlantListHandler.addPlantToList(p3);
        IndoorPlantListHandler.addPlantToList(p);
        IndoorPlantListHandler.addPlantToList(p);
        IndoorPlantListHandler.addPlantToList(p3);
        IndoorPlantListHandler.addPlantToList(p3);
        IndoorPlantListHandler.addPlantToList(p1);
        IndoorPlantListHandler.addPlantToList(p2);
        IndoorPlantListHandler.addPlantToList(p3);
        IndoorPlantListHandler.addPlantToList(p1);
        
        IndoorPlantListHandler.updateListPanel();
    }
    
    private void assignInventoryPanels()
    {
       
        InventoryController.setInventoryListPanel(this.inventoryListContainer);
        InventoryController.setInventorySelectionPanel(this.inventorySelectionPanel);
        //InventoryController.setInventoryTopPanel(this.inventoryTopPanel);
        InventoryController.setInventoryListContainer(inventoryListContainer);
        
        InventoryController.setItemNameField(this.inventoryItemNameField);
        InventoryController.setItemDateField(this.inventoryItemDateField);
        InventoryController.setItemBatchField(this.inventoryItemBatchField);
        InventoryController.setItemWeightField(this.inventoryItemWeightField);
        InventoryController.setInventoryAddItemPanel(this.inventoryAddItemPanel);
        InventoryController.setInventoryHomePanel(this.inventoryHomePanel);
        
        
    }
    
    private void assignSeedBankPanels()
    {
        SeedBankHandler.assignPanels(this.seedBankCellPanel, this.seedPackDisplayPanel, this.seedbankFilterPanel);
        
    }
    
    

    
   
    private ArrayList<JLabel> getLabelList()
    {
         ArrayList<JLabel> labelList = new ArrayList();
         labelList.add(homeLabel);
         labelList.add(bankLabel);
         labelList.add(inventoryLabel);
         labelList.add(indoorLabel);
         labelList.add(outdoorLabel);
         labelList.add(seedbankLabel);
         labelList.add(phenotypesLabel);
         labelList.add(clonesLabel);
         
         
         return labelList;
            
    }

   
    
    private ArrayList<JPanel> getPanelList()
    {
        ArrayList<JPanel> panelList = new ArrayList();
        
        panelList.add(dashboardPanel);
        panelList.add(bankPanel);
        panelList.add(inventoryPanel);
        panelList.add(indoorPanel);
        panelList.add(outdoorPanel);
        panelList.add(seedbankPanel);
        panelList.add(phenotypesPanel);
        panelList.add(clonesPanel);
        
        return panelList;
    }
    
    private ArrayList<JLabel> getSideLabels()
    {
        ArrayList<JLabel> labelList = new ArrayList();
        
        labelList.add(homeSideLabel);
        labelList.add(bankSideLabel);
        labelList.add(inventorySideLabel);
        labelList.add(indoorSideLabel);
        labelList.add(outdoorSideLabel);
        labelList.add(seedbankSideLabel);
        labelList.add(motherplantsSideLabel);
        labelList.add(clonesSideLabel);
        
        return labelList;
    }
    
    private ArrayList<JLabel> getMachine2SlotList()
    {
        ArrayList<JLabel> slotList = new ArrayList();
        
        slotList.add(CloneMachine2Slot1);
        slotList.add(CloneMachine2Slot2);
        slotList.add(CloneMachine2Slot3);
        slotList.add(CloneMachine2Slot4);
        slotList.add(CloneMachine2Slot5);
        slotList.add(CloneMachine2Slot6);
        slotList.add(CloneMachine2Slot7);
        slotList.add(CloneMachine2Slot8);
        slotList.add(CloneMachine2Slot9);
        slotList.add(CloneMachine2Slot10);
        slotList.add(CloneMachine2Slot11);
        slotList.add(CloneMachine2Slot12);
        slotList.add(CloneMachine2Slot13);
        slotList.add(CloneMachine2Slot14);
        slotList.add(CloneMachine2Slot15);
        slotList.add(CloneMachine2Slot16);
        slotList.add(CloneMachine2Slot17);
        slotList.add(CloneMachine2Slot18);
        slotList.add(CloneMachine2Slot19);
        slotList.add(CloneMachine2Slot20);
        slotList.add(CloneMachine2Slot21);
        slotList.add(CloneMachine2Slot22);
        slotList.add(CloneMachine2Slot23);
        slotList.add(CloneMachine2Slot24);
        slotList.add(CloneMachine2Slot25);
        slotList.add(CloneMachine2Slot26);
        slotList.add(CloneMachine2Slot27);
        slotList.add(CloneMachine2Slot28);
        slotList.add(CloneMachine2Slot29);
        slotList.add(CloneMachine2Slot30);
        slotList.add(CloneMachine2Slot31);
        slotList.add(CloneMachine2Slot32);
        slotList.add(CloneMachine2Slot34);
        slotList.add(CloneMachine2Slot35);

        return slotList;
    }
    
    private ArrayList<JLabel> getCloneSlotList()
    {
        ArrayList<JLabel> slotList = new ArrayList();
        
        slotList.add(this.A1);
        slotList.add(this.A2);
        slotList.add(this.A3);
        slotList.add(this.A4);
        slotList.add(this.A5);
        slotList.add(this.A6);
        
        slotList.add(this.B1);
        slotList.add(this.B2);
        slotList.add(this.B3);
        slotList.add(this.B4);
        slotList.add(this.B5);
        slotList.add(this.B6);
        
        slotList.add(this.C1);
        slotList.add(this.C2);
        slotList.add(this.C3);
        slotList.add(this.C4);
        slotList.add(this.C5);
        slotList.add(this.C6);
        
        
        slotList.add(this.D1);
        slotList.add(this.D2);
        slotList.add(this.D3);
        slotList.add(this.D4);
        slotList.add(this.D5);
        slotList.add(this.D6);
        
        slotList.add(this.E1);
        slotList.add(this.E2);
        slotList.add(this.E3);
        slotList.add(this.E4);
        slotList.add(this.E5);
        slotList.add(this.E6);
        
       
        
        slotList.add(this.F1);
        slotList.add(this.F2);
        slotList.add(this.F3);
        slotList.add(this.F4);
        slotList.add(this.F5);
        slotList.add(this.F6);
        
       
        
        
        
        
        return slotList;
    }
    
    
    private void setupPlantCreation()
    {
        PlantCreationHandler.setCultivarField(this.pcCultivarText);
        PlantCreationHandler.setPlantNumberField(this.pcPlantNumberText);
        PlantCreationHandler.setGrowIdField(this.pcGrowIdText);
    }

    
 
    
    private ArrayList<ArrayList<JLabel>> getPhenoFinderDisplayLabels()
    {
        ArrayList<ArrayList<JLabel>> multiList = new ArrayList();
        ArrayList<JLabel> idLabelList = new ArrayList();
        ArrayList<JLabel> strainLabelList = new ArrayList();
        ArrayList<JLabel> locationLabelList = new ArrayList();
        multiList.add(idLabelList);
        multiList.add(strainLabelList);
        multiList.add(locationLabelList);
        
        multiList.get(0).add(idCell1);
        multiList.get(0).add(idCell2);
        multiList.get(0).add(idCell3);
        multiList.get(0).add(idCell4);
        multiList.get(0).add(idCell5);
        multiList.get(0).add(idCell6);
        multiList.get(0).add(idCell7);
        multiList.get(0).add(idCell8);
        multiList.get(0).add(idCell9);
        multiList.get(0).add(idCell10);
        
        multiList.get(1).add(strainCell1);
        multiList.get(1).add(strainCell2);
        multiList.get(1).add(strainCell3);
        multiList.get(1).add(strainCell4);
        multiList.get(1).add(strainCell5);
        multiList.get(1).add(strainCell6);
        multiList.get(1).add(strainCell7);
        multiList.get(1).add(strainCell8);
        multiList.get(1).add(strainCell9);
        multiList.get(1).add(strainCell10);
         
        multiList.get(2).add(locationCell1);
        multiList.get(2).add(locationCell2);
        multiList.get(2).add(locationCell3);
        multiList.get(2).add(locationCell4);
        multiList.get(2).add(locationCell5);
        multiList.get(2).add(locationCell6);
        multiList.get(2).add(locationCell7);
        multiList.get(2).add(locationCell8);
        multiList.get(2).add(locationCell9);
        multiList.get(2).add(locationCell10);
        
        return multiList;
    }

    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel34 = new javax.swing.JLabel();
        navigationPane = new javax.swing.JLayeredPane();
        dragLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        settingsLabel = new javax.swing.JLabel();
        homeSideLabel = new javax.swing.JLabel();
        homeLabel = new javax.swing.JLabel();
        bankSideLabel = new javax.swing.JLabel();
        bankLabel = new javax.swing.JLabel();
        inventorySideLabel = new javax.swing.JLabel();
        inventoryLabel = new javax.swing.JLabel();
        indoorSideLabel = new javax.swing.JLabel();
        indoorLabel = new javax.swing.JLabel();
        outdoorSideLabel = new javax.swing.JLabel();
        outdoorLabel = new javax.swing.JLabel();
        seedbankSideLabel = new javax.swing.JLabel();
        seedbankLabel = new javax.swing.JLabel();
        motherplantsSideLabel = new javax.swing.JLabel();
        phenotypesLabel = new javax.swing.JLabel();
        clonesSideLabel = new javax.swing.JLabel();
        clonesLabel = new javax.swing.JLabel();
        contentLayeredPane = new javax.swing.JLayeredPane();
        dashboardPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        bankPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        inventoryPanel = new javax.swing.JPanel();
        inventoryDisplayPanel = new javax.swing.JPanel();
        inventorySidePanel = new javax.swing.JPanel();
        inventoryHomePanel = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        inventoryAddItemPanel = new javax.swing.JPanel();
        inventoryItemWeightField = new javax.swing.JTextField();
        inventoryItemBatchField = new javax.swing.JTextField();
        inventoryItemDateField = new javax.swing.JTextField();
        inventoryItemNameField = new javax.swing.JTextField();
        inventoryTypeOutdoorLabel = new javax.swing.JLabel();
        inventoryTypeIndoorLabel = new javax.swing.JLabel();
        inventoryAddItemSubmitLabel = new javax.swing.JLabel();
        inventoryHangingSelectionLabel = new javax.swing.JLabel();
        inventoryCuringSelectionLabel = new javax.swing.JLabel();
        inventoryCuredSelectionLabel = new javax.swing.JLabel();
        inventoryAddItemBackground = new javax.swing.JLabel();
        inventorySelectionPanel = new javax.swing.JPanel();
        inventorySelectionItemNameLabel = new javax.swing.JLabel();
        inventorySelectionIdLabel = new javax.swing.JLabel();
        inventorySelectionBatchIdLabel = new javax.swing.JLabel();
        inventoryProfileDataLabel = new javax.swing.JLabel();
        inventorySelectionPanelBackground = new javax.swing.JLabel();
        inventoryListContainer = new javax.swing.JPanel();
        inventoryListPanel = new javax.swing.JPanel();
        inventoryListPanelBackground = new javax.swing.JLabel();
        indoorPanel = new javax.swing.JPanel();
        IndoorModulePanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        SelectedGrowRoomPanel = new javax.swing.JPanel();
        customIndoorListPanel = new javax.swing.JPanel();
        growRoomPlantListPanel = new javax.swing.JLabel();
        growRoomOverviewPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        environmentalPanel = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        roomSpecBgLabel = new javax.swing.JLabel();
        growRoomToolNavBarPanel = new javax.swing.JPanel();
        growRoomToolWateringIcon = new javax.swing.JLabel();
        grwoRoomToolPlantIcon = new javax.swing.JLabel();
        growRoomLightToolIcon = new javax.swing.JLabel();
        growRoomToolPlantStatIcon = new javax.swing.JLabel();
        growRoomToolPanel = new javax.swing.JPanel();
        growroomPlantToolPanel = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        growRoomOverviewLabel = new javax.swing.JLabel();
        outdoorPanel = new javax.swing.JPanel();
        plantCreationPanel = new javax.swing.JPanel();
        pcGrowIdText = new javax.swing.JTextField();
        pcPlantNumberText = new javax.swing.JTextField();
        pcCultivarText = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        pcSubmitLabel = new javax.swing.JLabel();
        pcFlowerLabel = new javax.swing.JLabel();
        pcVegLabel = new javax.swing.JLabel();
        pcSeedingLabel = new javax.swing.JLabel();
        pcBgLabel = new javax.swing.JLabel();
        seedbankPanel = new javax.swing.JPanel();
        seedBankCellPanel = new javax.swing.JPanel();
        seedBankSidePanel = new javax.swing.JPanel();
        seedbankFilterPanel = new javax.swing.JPanel();
        addPackFilterPanelLabel = new javax.swing.JLabel();
        seedbankFilterBackgroundLabel = new javax.swing.JLabel();
        addPackCheckPanel = new javax.swing.JPanel();
        addPackConfirmationTrueLabel = new javax.swing.JLabel();
        addPackConfirmationFalseLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        addPackAddQuanitityLabel = new javax.swing.JLabel();
        addPackSubQuanitityLabel = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        addPackQuantityCounterLabel = new javax.swing.JLabel();
        addPackCheckPanelBackground = new javax.swing.JLabel();
        addPackPanel = new javax.swing.JPanel();
        helpAdminLabel = new javax.swing.JLabel();
        addPackClearLabel = new javax.swing.JLabel();
        addPackDescriptionArea = new javax.swing.JTextArea();
        addPackCultivarField = new javax.swing.JTextField();
        addPackYieldField = new javax.swing.JTextField();
        addPackSizeField = new javax.swing.JTextField();
        addPackTerpeneProfileField = new javax.swing.JTextField();
        addPackFloweringTimeField = new javax.swing.JTextField();
        addPackSeedTypeField = new javax.swing.JTextField();
        addPackLineageField = new javax.swing.JTextField();
        addPackBreederField = new javax.swing.JTextField();
        addPackReleaseField = new javax.swing.JTextField();
        addPackStockField = new javax.swing.JTextField();
        backToPackDisplayLabel = new javax.swing.JLabel();
        submitPackButton = new javax.swing.JLabel();
        seedPackDisplayBackground1 = new javax.swing.JLabel();
        seedPackDisplayPanel = new javax.swing.JPanel();
        packCultivarLabel = new javax.swing.JLabel();
        packImageLabel = new javax.swing.JLabel();
        packYieldLabel = new javax.swing.JLabel();
        packSizeLabel = new javax.swing.JLabel();
        packTerpeneLabel = new javax.swing.JLabel();
        packFloweringTimeLabel = new javax.swing.JLabel();
        packSeedTypeLabel = new javax.swing.JLabel();
        packLineageLabel = new javax.swing.JLabel();
        packReleaseLabel = new javax.swing.JLabel();
        packBreederLabel = new javax.swing.JLabel();
        seedStockLabel = new javax.swing.JLabel();
        packIdLabel = new javax.swing.JLabel();
        descriptionArea = new javax.swing.JTextArea();
        seedPackDisplayBackground = new javax.swing.JLabel();
        phenotypesPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        clonesPanel = new javax.swing.JPanel();
        CloneMachine2Panel = new javax.swing.JPanel();
        cloneUIPanel1 = new javax.swing.JPanel();
        CloneMachine2Slot1 = new javax.swing.JLabel();
        CloneMachine2Slot8 = new javax.swing.JLabel();
        CloneMachine2Slot15 = new javax.swing.JLabel();
        CloneMachine2Slot22 = new javax.swing.JLabel();
        CloneMachine2Slot29 = new javax.swing.JLabel();
        CloneMachine2Slot7 = new javax.swing.JLabel();
        CloneMachine2Slot2 = new javax.swing.JLabel();
        CloneMachine2Slot9 = new javax.swing.JLabel();
        CloneMachine2Slot16 = new javax.swing.JLabel();
        CloneMachine2Slot23 = new javax.swing.JLabel();
        CloneMachine2Slot30 = new javax.swing.JLabel();
        CloneMachine2Slot14 = new javax.swing.JLabel();
        CloneMachine2Slot3 = new javax.swing.JLabel();
        CloneMachine2Slot10 = new javax.swing.JLabel();
        CloneMachine2Slot17 = new javax.swing.JLabel();
        CloneMachine2Slot24 = new javax.swing.JLabel();
        CloneMachine2Slot31 = new javax.swing.JLabel();
        CloneMachine2Slot21 = new javax.swing.JLabel();
        CloneMachine2Slot4 = new javax.swing.JLabel();
        CloneMachine2Slot11 = new javax.swing.JLabel();
        CloneMachine2Slot18 = new javax.swing.JLabel();
        CloneMachine2Slot25 = new javax.swing.JLabel();
        CloneMachine2Slot32 = new javax.swing.JLabel();
        CloneMachine2Slot28 = new javax.swing.JLabel();
        CloneMachine2Slot5 = new javax.swing.JLabel();
        CloneMachine2Slot12 = new javax.swing.JLabel();
        CloneMachine2Slot19 = new javax.swing.JLabel();
        CloneMachine2Slot26 = new javax.swing.JLabel();
        CloneMachine2Slot33 = new javax.swing.JLabel();
        CloneMachine2Slot35 = new javax.swing.JLabel();
        CloneMachine2Slot6 = new javax.swing.JLabel();
        CloneMachine2Slot13 = new javax.swing.JLabel();
        CloneMachine2Slot20 = new javax.swing.JLabel();
        CloneMachine2Slot27 = new javax.swing.JLabel();
        CloneMachine2Slot34 = new javax.swing.JLabel();
        cloneMachineSidePanel = new javax.swing.JPanel();
        emptyCuttingSlotPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        viewCuttingMotherButton1 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        selectedCuttingPanel = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        viewCuttingMotherButton = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        cloneMachineDashboardPanel = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        cloneEventPanel = new javax.swing.JPanel();
        cloneEventPlantTag = new javax.swing.JLabel();
        cloneEventPlantId = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        cloneEventTextArea = new javax.swing.JTextArea();
        cloneEventBackground = new javax.swing.JLabel();
        supportPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cloneStartupPanel = new javax.swing.JPanel();
        cloneMachineSlot4 = new javax.swing.JPanel();
        cloneMachineSlot6 = new javax.swing.JPanel();
        cloneMachineSlot5 = new javax.swing.JPanel();
        cloneMachineSlot3 = new javax.swing.JPanel();
        cloneMachineSlot1 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        cloneMachineSlot2 = new javax.swing.JPanel();
        cloneMachineSlot9 = new javax.swing.JPanel();
        cloneMachineSlot8 = new javax.swing.JPanel();
        cloneMachineSlot7 = new javax.swing.JPanel();
        cloneSelectionPanel = new javax.swing.JPanel();
        cloneSelectionPanelSlotLabel = new javax.swing.JLabel();
        cloneSelectionPanelPlantIdText = new javax.swing.JLabel();
        cloneSelectionPanelMotherIdText = new javax.swing.JLabel();
        cloneSelectionPanelCutDateText = new javax.swing.JLabel();
        cloneSelectionPanelStrain = new javax.swing.JLabel();
        cloneSelectionPlantIdLabel = new javax.swing.JLabel();
        cloneSelectionMotherPlantIdLabel = new javax.swing.JLabel();
        cloneSelectionCutDateLabel = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        cloneSelectionPanelBG = new javax.swing.JLabel();
        phenoFinderPanel = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        phenoFinderSearchLabel = new javax.swing.JLabel();
        tablePanel = new javax.swing.JPanel();
        idTitleLabel = new javax.swing.JLabel();
        idCell1 = new javax.swing.JLabel();
        idCell2 = new javax.swing.JLabel();
        idCell3 = new javax.swing.JLabel();
        idCell4 = new javax.swing.JLabel();
        idCell5 = new javax.swing.JLabel();
        idCell6 = new javax.swing.JLabel();
        idCell7 = new javax.swing.JLabel();
        idCell8 = new javax.swing.JLabel();
        idCell9 = new javax.swing.JLabel();
        idCell10 = new javax.swing.JLabel();
        strainCell1 = new javax.swing.JLabel();
        strainCell2 = new javax.swing.JLabel();
        strainCell3 = new javax.swing.JLabel();
        strainCell4 = new javax.swing.JLabel();
        strainCell5 = new javax.swing.JLabel();
        strainCell6 = new javax.swing.JLabel();
        strainCell7 = new javax.swing.JLabel();
        strainCell8 = new javax.swing.JLabel();
        strainCell9 = new javax.swing.JLabel();
        strainCell10 = new javax.swing.JLabel();
        locationCell1 = new javax.swing.JLabel();
        locationCell2 = new javax.swing.JLabel();
        locationCell3 = new javax.swing.JLabel();
        locationCell4 = new javax.swing.JLabel();
        locationCell5 = new javax.swing.JLabel();
        locationCell6 = new javax.swing.JLabel();
        locationCell7 = new javax.swing.JLabel();
        locationCell8 = new javax.swing.JLabel();
        locationCell9 = new javax.swing.JLabel();
        locationCell10 = new javax.swing.JLabel();
        locationTitleLab = new javax.swing.JLabel();
        strainTitleLabel = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        editSlotPanel = new javax.swing.JPanel();
        searchIconLabel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        addCloneLabel = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        strainLabel = new javax.swing.JLabel();
        locationLabel = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        nLabel2 = new javax.swing.JLabel();
        nLabel1 = new javax.swing.JLabel();
        nLabel = new javax.swing.JLabel();
        searchFeedbackLabel = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        motherIdTextField = new javax.swing.JTextField();
        editSlotBackground = new javax.swing.JLabel();
        backgroundBlurLabel = new javax.swing.JLabel();
        cloneUIPanel = new javax.swing.JPanel();
        A1 = new javax.swing.JLabel();
        A2 = new javax.swing.JLabel();
        A3 = new javax.swing.JLabel();
        A4 = new javax.swing.JLabel();
        A5 = new javax.swing.JLabel();
        A6 = new javax.swing.JLabel();
        B1 = new javax.swing.JLabel();
        B2 = new javax.swing.JLabel();
        B3 = new javax.swing.JLabel();
        B4 = new javax.swing.JLabel();
        B5 = new javax.swing.JLabel();
        B6 = new javax.swing.JLabel();
        C1 = new javax.swing.JLabel();
        C2 = new javax.swing.JLabel();
        C3 = new javax.swing.JLabel();
        C4 = new javax.swing.JLabel();
        C5 = new javax.swing.JLabel();
        C6 = new javax.swing.JLabel();
        D1 = new javax.swing.JLabel();
        D2 = new javax.swing.JLabel();
        D3 = new javax.swing.JLabel();
        D4 = new javax.swing.JLabel();
        D5 = new javax.swing.JLabel();
        D6 = new javax.swing.JLabel();
        E1 = new javax.swing.JLabel();
        E2 = new javax.swing.JLabel();
        E3 = new javax.swing.JLabel();
        E4 = new javax.swing.JLabel();
        E5 = new javax.swing.JLabel();
        E6 = new javax.swing.JLabel();
        F1 = new javax.swing.JLabel();
        F2 = new javax.swing.JLabel();
        F3 = new javax.swing.JLabel();
        F4 = new javax.swing.JLabel();
        F5 = new javax.swing.JLabel();
        F6 = new javax.swing.JLabel();

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/Panels/plantListGraphic.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        navigationPane.setBackground(new java.awt.Color(55, 62, 72));
        navigationPane.setOpaque(true);
        navigationPane.add(dragLabel);
        dragLabel.setBounds(0, 0, 1260, 100);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Version 0.0.1");
        navigationPane.add(jLabel3);
        jLabel3.setBounds(0, 80, 100, 20);

        jLabel1.setBackground(new java.awt.Color(33, 38, 44));
        jLabel1.setOpaque(true);
        navigationPane.add(jLabel1);
        jLabel1.setBounds(0, 0, 100, 100);

        settingsLabel.setBackground(new java.awt.Color(33, 38, 44));
        settingsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        settingsLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Navigation/settingsIcon.png"))); // NOI18N
        settingsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settingsLabel.setOpaque(true);
        settingsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                settingsLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                settingsLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                settingsLabelMouseReleased(evt);
            }
        });
        navigationPane.add(settingsLabel);
        settingsLabel.setBounds(0, 820, 100, 93);

        homeSideLabel.setBackground(new java.awt.Color(51, 255, 255));
        homeSideLabel.setOpaque(true);
        navigationPane.add(homeSideLabel);
        homeSideLabel.setBounds(0, 100, 5, 90);

        homeLabel.setBackground(new java.awt.Color(55, 62, 72));
        homeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        homeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Navigation/homeIcon.png"))); // NOI18N
        homeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        homeLabel.setOpaque(true);
        homeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                homeLabelMouseReleased(evt);
            }
        });
        navigationPane.add(homeLabel);
        homeLabel.setBounds(0, 100, 100, 90);

        bankSideLabel.setBackground(new java.awt.Color(51, 255, 255));
        bankSideLabel.setOpaque(true);
        navigationPane.add(bankSideLabel);
        bankSideLabel.setBounds(0, 190, 5, 90);

        bankLabel.setBackground(new java.awt.Color(55, 62, 72));
        bankLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bankLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Navigation/bankIcon.png"))); // NOI18N
        bankLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bankLabel.setOpaque(true);
        bankLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bankLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bankLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bankLabelMouseReleased(evt);
            }
        });
        navigationPane.add(bankLabel);
        bankLabel.setBounds(0, 190, 100, 90);

        inventorySideLabel.setBackground(new java.awt.Color(51, 255, 255));
        inventorySideLabel.setOpaque(true);
        navigationPane.add(inventorySideLabel);
        inventorySideLabel.setBounds(0, 280, 5, 90);

        inventoryLabel.setBackground(new java.awt.Color(55, 62, 72));
        inventoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inventoryLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Navigation/inventoryIcon.png"))); // NOI18N
        inventoryLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        inventoryLabel.setOpaque(true);
        inventoryLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                inventoryLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                inventoryLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                inventoryLabelMouseReleased(evt);
            }
        });
        navigationPane.add(inventoryLabel);
        inventoryLabel.setBounds(0, 280, 100, 90);

        indoorSideLabel.setBackground(new java.awt.Color(51, 255, 255));
        indoorSideLabel.setOpaque(true);
        navigationPane.add(indoorSideLabel);
        indoorSideLabel.setBounds(0, 370, 5, 90);

        indoorLabel.setBackground(new java.awt.Color(55, 62, 72));
        indoorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        indoorLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Navigation/indoorIcon.png"))); // NOI18N
        indoorLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        indoorLabel.setOpaque(true);
        indoorLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                indoorLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                indoorLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                indoorLabelMouseReleased(evt);
            }
        });
        navigationPane.add(indoorLabel);
        indoorLabel.setBounds(0, 370, 100, 90);

        outdoorSideLabel.setBackground(new java.awt.Color(51, 255, 255));
        outdoorSideLabel.setOpaque(true);
        navigationPane.add(outdoorSideLabel);
        outdoorSideLabel.setBounds(0, 460, 5, 90);

        outdoorLabel.setBackground(new java.awt.Color(55, 62, 72));
        outdoorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outdoorLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Navigation/outdoorIcon.png"))); // NOI18N
        outdoorLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        outdoorLabel.setOpaque(true);
        outdoorLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                outdoorLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                outdoorLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                outdoorLabelMouseReleased(evt);
            }
        });
        navigationPane.add(outdoorLabel);
        outdoorLabel.setBounds(0, 460, 100, 90);

        seedbankSideLabel.setBackground(new java.awt.Color(51, 255, 255));
        seedbankSideLabel.setOpaque(true);
        navigationPane.add(seedbankSideLabel);
        seedbankSideLabel.setBounds(0, 550, 5, 90);

        seedbankLabel.setBackground(new java.awt.Color(55, 62, 72));
        seedbankLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        seedbankLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Navigation/seedbankIcon.png"))); // NOI18N
        seedbankLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        seedbankLabel.setOpaque(true);
        seedbankLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                seedbankLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                seedbankLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                seedbankLabelMouseReleased(evt);
            }
        });
        navigationPane.add(seedbankLabel);
        seedbankLabel.setBounds(0, 550, 100, 90);

        motherplantsSideLabel.setBackground(new java.awt.Color(51, 255, 255));
        motherplantsSideLabel.setOpaque(true);
        navigationPane.add(motherplantsSideLabel);
        motherplantsSideLabel.setBounds(0, 640, 5, 90);

        phenotypesLabel.setBackground(new java.awt.Color(55, 62, 72));
        phenotypesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        phenotypesLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Navigation/phenotypesIcon.png"))); // NOI18N
        phenotypesLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        phenotypesLabel.setOpaque(true);
        phenotypesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                phenotypesLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                phenotypesLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                phenotypesLabelMouseReleased(evt);
            }
        });
        navigationPane.add(phenotypesLabel);
        phenotypesLabel.setBounds(0, 640, 100, 90);

        clonesSideLabel.setBackground(new java.awt.Color(51, 255, 255));
        clonesSideLabel.setOpaque(true);
        navigationPane.add(clonesSideLabel);
        clonesSideLabel.setBounds(0, 730, 5, 90);

        clonesLabel.setBackground(new java.awt.Color(55, 62, 72));
        clonesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clonesLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Navigation/clonesIcon.png"))); // NOI18N
        clonesLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clonesLabel.setOpaque(true);
        clonesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clonesLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clonesLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                clonesLabelMouseReleased(evt);
            }
        });
        navigationPane.add(clonesLabel);
        clonesLabel.setBounds(0, 730, 100, 90);

        getContentPane().add(navigationPane);
        navigationPane.setBounds(0, 0, 100, 940);

        contentLayeredPane.setBackground(new java.awt.Color(42, 46, 45));
        contentLayeredPane.setOpaque(true);
        contentLayeredPane.setPreferredSize(new java.awt.Dimension(1160, 915));

        dashboardPanel.setBackground(new java.awt.Color(42, 46, 45));
        dashboardPanel.setLayout(null);

        jButton1.setText("warning");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        dashboardPanel.add(jButton1);
        jButton1.setBounds(320, 810, 73, 22);

        jButton2.setText("success");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        dashboardPanel.add(jButton2);
        jButton2.setBounds(330, 720, 70, 22);

        jButton3.setText("info");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        dashboardPanel.add(jButton3);
        jButton3.setBounds(330, 770, 51, 22);

        jPanel1.setLayout(null);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/GrowModules/DemoModule.png"))); // NOI18N
        jLabel6.setText("jLabel6");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(0, 0, 507, 201);

        jLabel7.setText("jLabel7");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(380, 130, 120, 60);

        dashboardPanel.add(jPanel1);
        jPanel1.setBounds(220, 210, 510, 200);

        contentLayeredPane.add(dashboardPanel);
        dashboardPanel.setBounds(0, 0, 1160, 915);

        bankPanel.setBackground(new java.awt.Color(42, 46, 45));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("This is the Bank");

        javax.swing.GroupLayout bankPanelLayout = new javax.swing.GroupLayout(bankPanel);
        bankPanel.setLayout(bankPanelLayout);
        bankPanelLayout.setHorizontalGroup(
            bankPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankPanelLayout.createSequentialGroup()
                .addGap(409, 409, 409)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(580, Short.MAX_VALUE))
        );
        bankPanelLayout.setVerticalGroup(
            bankPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankPanelLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 862, Short.MAX_VALUE))
        );

        contentLayeredPane.add(bankPanel);
        bankPanel.setBounds(0, 0, 1160, 915);

        inventoryPanel.setBackground(new java.awt.Color(42, 46, 45));
        inventoryPanel.setLayout(null);

        inventoryDisplayPanel.setOpaque(false);
        inventoryDisplayPanel.setLayout(null);

        inventorySidePanel.setOpaque(false);
        inventorySidePanel.setLayout(null);

        inventoryHomePanel.setLayout(null);

        jLabel29.setText("add");
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel29MouseReleased(evt);
            }
        });
        inventoryHomePanel.add(jLabel29);
        jLabel29.setBounds(40, 290, 140, 80);

        jLabel28.setText("Home");
        inventoryHomePanel.add(jLabel28);
        jLabel28.setBounds(90, 70, 140, 80);

        inventorySidePanel.add(inventoryHomePanel);
        inventoryHomePanel.setBounds(0, 0, 330, 880);

        inventoryAddItemPanel.setOpaque(false);
        inventoryAddItemPanel.setLayout(null);

        inventoryItemWeightField.setBackground(new java.awt.Color(255,255,255,0));
        inventoryItemWeightField.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        inventoryItemWeightField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inventoryItemWeightField.setBorder(null);
        inventoryItemWeightField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inventoryItemWeightFieldKeyReleased(evt);
            }
        });
        inventoryAddItemPanel.add(inventoryItemWeightField);
        inventoryItemWeightField.setBounds(40, 700, 250, 30);

        inventoryItemBatchField.setBackground(new java.awt.Color(255,255,255,0));
        inventoryItemBatchField.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        inventoryItemBatchField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inventoryItemBatchField.setBorder(null);
        inventoryItemBatchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inventoryItemBatchFieldKeyReleased(evt);
            }
        });
        inventoryAddItemPanel.add(inventoryItemBatchField);
        inventoryItemBatchField.setBounds(40, 440, 250, 30);

        inventoryItemDateField.setBackground(new java.awt.Color(255,255,255,0));
        inventoryItemDateField.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        inventoryItemDateField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inventoryItemDateField.setBorder(null);
        inventoryItemDateField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inventoryItemDateFieldKeyReleased(evt);
            }
        });
        inventoryAddItemPanel.add(inventoryItemDateField);
        inventoryItemDateField.setBounds(40, 220, 250, 30);

        inventoryItemNameField.setBackground(new java.awt.Color(255,255,255,0));
        inventoryItemNameField.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        inventoryItemNameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inventoryItemNameField.setBorder(null);
        inventoryItemNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inventoryItemNameFieldKeyReleased(evt);
            }
        });
        inventoryAddItemPanel.add(inventoryItemNameField);
        inventoryItemNameField.setBounds(40, 100, 250, 30);

        inventoryTypeOutdoorLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                inventoryTypeOutdoorLabelMouseReleased(evt);
            }
        });
        inventoryAddItemPanel.add(inventoryTypeOutdoorLabel);
        inventoryTypeOutdoorLabel.setBounds(180, 330, 90, 40);

        inventoryTypeIndoorLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                inventoryTypeIndoorLabelMouseReleased(evt);
            }
        });
        inventoryAddItemPanel.add(inventoryTypeIndoorLabel);
        inventoryTypeIndoorLabel.setBounds(50, 330, 90, 40);

        inventoryAddItemSubmitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                inventoryAddItemSubmitLabelMouseReleased(evt);
            }
        });
        inventoryAddItemPanel.add(inventoryAddItemSubmitLabel);
        inventoryAddItemSubmitLabel.setBounds(82, 798, 150, 50);

        inventoryHangingSelectionLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                inventoryHangingSelectionLabelMouseReleased(evt);
            }
        });
        inventoryAddItemPanel.add(inventoryHangingSelectionLabel);
        inventoryHangingSelectionLabel.setBounds(230, 585, 70, 22);

        inventoryCuringSelectionLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                inventoryCuringSelectionLabelMouseReleased(evt);
            }
        });
        inventoryAddItemPanel.add(inventoryCuringSelectionLabel);
        inventoryCuringSelectionLabel.setBounds(125, 585, 70, 22);

        inventoryCuredSelectionLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                inventoryCuredSelectionLabelMouseReleased(evt);
            }
        });
        inventoryAddItemPanel.add(inventoryCuredSelectionLabel);
        inventoryCuredSelectionLabel.setBounds(30, 585, 70, 22);

        inventoryAddItemBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Inventory/Graphics/addItemPanel.png"))); // NOI18N
        inventoryAddItemPanel.add(inventoryAddItemBackground);
        inventoryAddItemBackground.setBounds(0, 0, 331, 880);

        inventorySidePanel.add(inventoryAddItemPanel);
        inventoryAddItemPanel.setBounds(0, 0, 330, 880);

        inventorySelectionPanel.setOpaque(false);
        inventorySelectionPanel.setLayout(null);

        inventorySelectionItemNameLabel.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        inventorySelectionItemNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        inventorySelectionItemNameLabel.setText("MindFlayer #5");
        inventorySelectionPanel.add(inventorySelectionItemNameLabel);
        inventorySelectionItemNameLabel.setBounds(70, 15, 170, 30);

        inventorySelectionIdLabel.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        inventorySelectionIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        inventorySelectionIdLabel.setText("ID: 0001");
        inventorySelectionPanel.add(inventorySelectionIdLabel);
        inventorySelectionIdLabel.setBounds(70, 40, 150, 20);

        inventorySelectionBatchIdLabel.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        inventorySelectionBatchIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inventorySelectionBatchIdLabel.setText("0001");
        inventorySelectionPanel.add(inventorySelectionBatchIdLabel);
        inventorySelectionBatchIdLabel.setBounds(245, 35, 75, 25);

        inventoryProfileDataLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        inventoryProfileDataLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                inventoryProfileDataLabelMouseReleased(evt);
            }
        });
        inventorySelectionPanel.add(inventoryProfileDataLabel);
        inventoryProfileDataLabel.setBounds(270, 100, 40, 30);

        inventorySelectionPanelBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Inventory/Graphics/selectionDisplayPanel.png"))); // NOI18N
        inventorySelectionPanel.add(inventorySelectionPanelBackground);
        inventorySelectionPanelBackground.setBounds(0, 0, 331, 880);

        inventorySidePanel.add(inventorySelectionPanel);
        inventorySelectionPanel.setBounds(0, 0, 330, 880);

        inventoryDisplayPanel.add(inventorySidePanel);
        inventorySidePanel.setBounds(815, 20, 330, 880);

        inventoryListContainer.setOpaque(false);
        inventoryListContainer.setLayout(null);
        inventoryDisplayPanel.add(inventoryListContainer);
        inventoryListContainer.setBounds(15, 20, 785, 880);

        inventoryListPanel.setOpaque(false);
        inventoryListPanel.setLayout(null);

        inventoryListPanelBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Inventory/Graphics/inventoryPanelListGraphic1.png"))); // NOI18N
        inventoryListPanel.add(inventoryListPanelBackground);
        inventoryListPanelBackground.setBounds(0, 0, 785, 880);

        inventoryDisplayPanel.add(inventoryListPanel);
        inventoryListPanel.setBounds(15, 20, 785, 880);

        inventoryPanel.add(inventoryDisplayPanel);
        inventoryDisplayPanel.setBounds(0, 0, 1160, 915);

        contentLayeredPane.add(inventoryPanel);
        inventoryPanel.setBounds(0, 0, 1160, 915);

        indoorPanel.setBackground(new java.awt.Color(42, 46, 45));
        indoorPanel.setLayout(null);

        IndoorModulePanel.setBackground(new java.awt.Color(42, 46, 45));
        IndoorModulePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                IndoorModulePanelMouseEntered(evt);
            }
        });
        IndoorModulePanel.setLayout(null);

        jPanel9.setOpaque(false);
        jPanel9.setLayout(null);
        IndoorModulePanel.add(jPanel9);
        jPanel9.setBounds(0, 0, 1160, 915);

        indoorPanel.add(IndoorModulePanel);
        IndoorModulePanel.setBounds(0, 0, 1160, 915);

        SelectedGrowRoomPanel.setBackground(new java.awt.Color(0, 0, 0));
        SelectedGrowRoomPanel.setEnabled(false);
        SelectedGrowRoomPanel.setLayout(null);

        customIndoorListPanel.setBackground(new java.awt.Color(255, 102, 255));
        customIndoorListPanel.setForeground(new java.awt.Color(255, 255, 0));
        customIndoorListPanel.setOpaque(false);
        customIndoorListPanel.setPreferredSize(new java.awt.Dimension(375, 894));

        growRoomPlantListPanel.setBackground(new java.awt.Color(0, 0, 0));
        growRoomPlantListPanel.setForeground(new java.awt.Color(0, 0, 0));
        growRoomPlantListPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/Panels/plantListGraphic.png"))); // NOI18N
        growRoomPlantListPanel.setText("jLabel35");

        javax.swing.GroupLayout customIndoorListPanelLayout = new javax.swing.GroupLayout(customIndoorListPanel);
        customIndoorListPanel.setLayout(customIndoorListPanelLayout);
        customIndoorListPanelLayout.setHorizontalGroup(
            customIndoorListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(growRoomPlantListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        customIndoorListPanelLayout.setVerticalGroup(
            customIndoorListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(growRoomPlantListPanel)
        );

        SelectedGrowRoomPanel.add(customIndoorListPanel);
        customIndoorListPanel.setBounds(10, 10, 375, 894);

        growRoomOverviewPanel.setEnabled(false);
        growRoomOverviewPanel.setOpaque(false);
        growRoomOverviewPanel.setLayout(null);

        jPanel4.setOpaque(false);

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/Panels/growRoomLightPanel.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        growRoomOverviewPanel.add(jPanel4);
        jPanel4.setBounds(615, 380, 126, 126);

        environmentalPanel.setOpaque(false);
        environmentalPanel.setLayout(null);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("62");
        environmentalPanel.add(jLabel26);
        jLabel26.setBounds(58, 98, 30, 20);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("62");
        environmentalPanel.add(jLabel5);
        jLabel5.setBounds(8, 98, 30, 20);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("75°");
        environmentalPanel.add(jLabel35);
        jLabel35.setBounds(180, 95, 30, 25);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("75°");
        environmentalPanel.add(jLabel31);
        jLabel31.setBounds(130, 95, 30, 25);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("75°");
        environmentalPanel.add(jLabel27);
        jLabel27.setBounds(150, 35, 45, 30);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("50");
        environmentalPanel.add(jLabel2);
        jLabel2.setBounds(28, 35, 34, 30);

        roomSpecBgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/Panels/growRoomSpecsPanel.png"))); // NOI18N
        environmentalPanel.add(roomSpecBgLabel);
        roomSpecBgLabel.setBounds(0, 0, 230, 126);

        growRoomOverviewPanel.add(environmentalPanel);
        environmentalPanel.setBounds(381, 380, 230, 126);

        growRoomToolNavBarPanel.setOpaque(false);
        growRoomToolNavBarPanel.setLayout(null);

        growRoomToolWateringIcon.setBackground(new java.awt.Color(38, 38, 38));
        growRoomToolWateringIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/Panels/wateringIcon.png"))); // NOI18N
        growRoomToolNavBarPanel.add(growRoomToolWateringIcon);
        growRoomToolWateringIcon.setBounds(0, 0, 30, 30);

        grwoRoomToolPlantIcon.setBackground(new java.awt.Color(38, 38, 38));
        grwoRoomToolPlantIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/Panels/plantIcon.png"))); // NOI18N
        growRoomToolNavBarPanel.add(grwoRoomToolPlantIcon);
        grwoRoomToolPlantIcon.setBounds(80, 0, 30, 30);

        growRoomLightToolIcon.setBackground(new java.awt.Color(38, 38, 38));
        growRoomLightToolIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/Panels/growLightIcon.png"))); // NOI18N
        growRoomToolNavBarPanel.add(growRoomLightToolIcon);
        growRoomLightToolIcon.setBounds(40, 0, 30, 30);

        growRoomToolPlantStatIcon.setBackground(new java.awt.Color(38, 38, 38));
        growRoomToolPlantStatIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/Panels/plantStatsIcon.png"))); // NOI18N
        growRoomToolNavBarPanel.add(growRoomToolPlantStatIcon);
        growRoomToolPlantStatIcon.setBounds(120, 0, 30, 30);

        growRoomOverviewPanel.add(growRoomToolNavBarPanel);
        growRoomToolNavBarPanel.setBounds(565, 535, 150, 30);

        growRoomToolPanel.setOpaque(false);
        growRoomToolPanel.setLayout(null);

        growroomPlantToolPanel.setEnabled(false);
        growroomPlantToolPanel.setOpaque(false);
        growroomPlantToolPanel.setLayout(null);

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/Panels/growroomPlantToolPanel.png"))); // NOI18N
        growroomPlantToolPanel.add(jLabel30);
        jLabel30.setBounds(0, 0, 725, 296);

        growRoomToolPanel.add(growroomPlantToolPanel);
        growroomPlantToolPanel.setBounds(0, 0, 725, 296);

        growRoomOverviewPanel.add(growRoomToolPanel);
        growRoomToolPanel.setBounds(15, 575, 720, 310);

        growRoomOverviewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Indoor/Panels/growroomSelectionPanel.png"))); // NOI18N
        growRoomOverviewPanel.add(growRoomOverviewLabel);
        growRoomOverviewLabel.setBounds(0, 0, 750, 895);

        SelectedGrowRoomPanel.add(growRoomOverviewPanel);
        growRoomOverviewPanel.setBounds(400, 10, 750, 895);

        indoorPanel.add(SelectedGrowRoomPanel);
        SelectedGrowRoomPanel.setBounds(0, 0, 1160, 915);

        contentLayeredPane.add(indoorPanel);
        indoorPanel.setBounds(0, 0, 1160, 915);

        outdoorPanel.setBackground(new java.awt.Color(42, 46, 45));
        outdoorPanel.setLayout(null);

        plantCreationPanel.setOpaque(false);
        plantCreationPanel.setLayout(null);

        pcGrowIdText.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        pcGrowIdText.setForeground(new java.awt.Color(255, 255, 255));
        pcGrowIdText.setBorder(null);
        pcGrowIdText.setBackground(new java.awt.Color(255,255,255,0));
        plantCreationPanel.add(pcGrowIdText);
        pcGrowIdText.setBounds(120, 140, 160, 30);

        pcPlantNumberText.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        pcPlantNumberText.setForeground(new java.awt.Color(255, 255, 255));
        pcPlantNumberText.setBorder(null);
        pcPlantNumberText.setBackground(new java.awt.Color(255,255,255,0));
        plantCreationPanel.add(pcPlantNumberText);
        pcPlantNumberText.setBounds(190, 100, 150, 30);

        pcCultivarText.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        pcCultivarText.setForeground(new java.awt.Color(255, 255, 255));
        pcCultivarText.setBorder(null);
        pcCultivarText.setBackground(new java.awt.Color(255,255,255,0));
        plantCreationPanel.add(pcCultivarText);
        pcCultivarText.setBounds(190, 55, 150, 30);
        plantCreationPanel.add(jLabel44);
        jLabel44.setBounds(180, 475, 95, 55);

        pcSubmitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pcSubmitLabelMouseReleased(evt);
            }
        });
        plantCreationPanel.add(pcSubmitLabel);
        pcSubmitLabel.setBounds(45, 470, 95, 55);

        pcFlowerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pcFlowerLabelMouseReleased(evt);
            }
        });
        plantCreationPanel.add(pcFlowerLabel);
        pcFlowerLabel.setBounds(120, 360, 95, 50);

        pcVegLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pcVegLabelMouseReleased(evt);
            }
        });
        plantCreationPanel.add(pcVegLabel);
        pcVegLabel.setBounds(190, 280, 90, 60);

        pcSeedingLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pcSeedingLabelMouseReleased(evt);
            }
        });
        plantCreationPanel.add(pcSeedingLabel);
        pcSeedingLabel.setBounds(50, 285, 100, 55);

        pcBgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Misc/plantCreationPanel.png"))); // NOI18N
        plantCreationPanel.add(pcBgLabel);
        pcBgLabel.setBounds(0, 0, 348, 554);

        outdoorPanel.add(plantCreationPanel);
        plantCreationPanel.setBounds(180, 60, 348, 554);

        contentLayeredPane.add(outdoorPanel);
        outdoorPanel.setBounds(0, 0, 1160, 915);

        seedbankPanel.setBackground(new java.awt.Color(42, 46, 45));
        seedbankPanel.setLayout(null);

        seedBankCellPanel.setBackground(new java.awt.Color(255, 255, 255));
        seedBankCellPanel.setOpaque(false);
        seedBankCellPanel.setLayout(null);
        seedbankPanel.add(seedBankCellPanel);
        seedBankCellPanel.setBounds(10, 10, 725, 880);

        seedBankSidePanel.setOpaque(false);
        seedBankSidePanel.setLayout(null);

        seedbankFilterPanel.setOpaque(false);
        seedbankFilterPanel.setLayout(null);

        addPackFilterPanelLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addPackFilterPanelLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addPackFilterPanelLabelMouseReleased(evt);
            }
        });
        seedbankFilterPanel.add(addPackFilterPanelLabel);
        addPackFilterPanelLabel.setBounds(20, 25, 140, 40);

        seedbankFilterBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/seedbank/seedbankHomeIcon.png"))); // NOI18N
        seedbankFilterPanel.add(seedbankFilterBackgroundLabel);
        seedbankFilterBackgroundLabel.setBounds(0, 0, 391, 891);

        seedBankSidePanel.add(seedbankFilterPanel);
        seedbankFilterPanel.setBounds(0, 0, 390, 890);

        addPackCheckPanel.setEnabled(false);
        addPackCheckPanel.setOpaque(false);
        addPackCheckPanel.setLayout(null);

        addPackConfirmationTrueLabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        addPackConfirmationTrueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addPackConfirmationTrueLabel.setText("Yes");
        addPackConfirmationTrueLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addPackConfirmationTrueLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addPackConfirmationTrueLabelMouseReleased(evt);
            }
        });
        addPackCheckPanel.add(addPackConfirmationTrueLabel);
        addPackConfirmationTrueLabel.setBounds(30, 115, 70, 30);

        addPackConfirmationFalseLabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        addPackConfirmationFalseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addPackConfirmationFalseLabel.setText("Back");
        addPackConfirmationFalseLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addPackConfirmationFalseLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addPackConfirmationFalseLabelMouseReleased(evt);
            }
        });
        addPackCheckPanel.add(addPackConfirmationFalseLabel);
        addPackConfirmationFalseLabel.setBounds(170, 115, 70, 30);

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Do you wish to add these pack(s)?");
        addPackCheckPanel.add(jLabel8);
        jLabel8.setBounds(10, 0, 250, 30);

        addPackAddQuanitityLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/seedbank/addPackConfirmAddQuanitity.png"))); // NOI18N
        addPackAddQuanitityLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addPackAddQuanitityLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addPackAddQuanitityLabelMouseReleased(evt);
            }
        });
        addPackCheckPanel.add(addPackAddQuanitityLabel);
        addPackAddQuanitityLabel.setBounds(98, 50, 30, 30);

        addPackSubQuanitityLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/seedbank/addPackConfirmSubQuantity.png"))); // NOI18N
        addPackSubQuanitityLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addPackSubQuanitityLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addPackSubQuanitityLabelMouseReleased(evt);
            }
        });
        addPackCheckPanel.add(addPackSubQuanitityLabel);
        addPackSubQuanitityLabel.setBounds(2, 50, 30, 30);

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/seedbank/addPackConfirmationCalendar.png"))); // NOI18N
        addPackCheckPanel.add(jLabel38);
        jLabel38.setBounds(180, 40, 50, 50);

        addPackQuantityCounterLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        addPackQuantityCounterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addPackQuantityCounterLabel.setText("0");
        addPackCheckPanel.add(addPackQuantityCounterLabel);
        addPackQuantityCounterLabel.setBounds(38, 44, 54, 40);

        addPackCheckPanelBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/seedbank/confirmAddPackGrahpic.png"))); // NOI18N
        addPackCheckPanel.add(addPackCheckPanelBackground);
        addPackCheckPanelBackground.setBounds(0, 0, 270, 150);

        seedBankSidePanel.add(addPackCheckPanel);
        addPackCheckPanel.setBounds(70, 580, 270, 150);

        addPackPanel.setBackground(new java.awt.Color(255, 255, 255));
        addPackPanel.setOpaque(false);
        addPackPanel.setLayout(null);

        helpAdminLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                helpAdminLabelMouseReleased(evt);
            }
        });
        addPackPanel.add(helpAdminLabel);
        helpAdminLabel.setBounds(205, 825, 75, 55);

        addPackClearLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addPackClearLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addPackClearLabelMouseReleased(evt);
            }
        });
        addPackPanel.add(addPackClearLabel);
        addPackClearLabel.setBounds(110, 825, 75, 55);

        addPackDescriptionArea.setBackground(new java.awt.Color(204, 204, 204));
        addPackDescriptionArea.setColumns(20);
        addPackDescriptionArea.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        addPackDescriptionArea.setLineWrap(true);
        addPackDescriptionArea.setRows(5);
        addPackDescriptionArea.setToolTipText("");
        addPackDescriptionArea.setWrapStyleWord(true);
        addPackDescriptionArea.setOpaque(false);
        addPackPanel.add(addPackDescriptionArea);
        addPackDescriptionArea.setBounds(5, 690, 380, 120);

        addPackCultivarField.setFont(new java.awt.Font("Cambria", 0, 36)); // NOI18N
        addPackCultivarField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        addPackCultivarField.setBorder(null);
        addPackPanel.add(addPackCultivarField);
        addPackCultivarField.setBounds(20, 2, 350, 40);

        addPackYieldField.setBackground(new java.awt.Color(237, 237, 237));
        addPackYieldField.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        addPackYieldField.setBorder(null);
        addPackPanel.add(addPackYieldField);
        addPackYieldField.setBounds(250, 650, 130, 35);

        addPackSizeField.setBackground(new java.awt.Color(237, 237, 237));
        addPackSizeField.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        addPackSizeField.setBorder(null);
        addPackPanel.add(addPackSizeField);
        addPackSizeField.setBounds(60, 650, 120, 35);

        addPackTerpeneProfileField.setBackground(new java.awt.Color(204, 204, 204));
        addPackTerpeneProfileField.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        addPackTerpeneProfileField.setBorder(null);
        addPackPanel.add(addPackTerpeneProfileField);
        addPackTerpeneProfileField.setBounds(150, 607, 230, 35);

        addPackFloweringTimeField.setBackground(new java.awt.Color(237, 237, 237));
        addPackFloweringTimeField.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        addPackFloweringTimeField.setBorder(null);
        addPackPanel.add(addPackFloweringTimeField);
        addPackFloweringTimeField.setBounds(150, 565, 230, 35);

        addPackSeedTypeField.setBackground(new java.awt.Color(204, 204, 204));
        addPackSeedTypeField.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        addPackSeedTypeField.setBorder(null);
        addPackPanel.add(addPackSeedTypeField);
        addPackSeedTypeField.setBounds(110, 522, 270, 35);

        addPackLineageField.setBackground(new java.awt.Color(237, 237, 237));
        addPackLineageField.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        addPackLineageField.setBorder(null);
        addPackPanel.add(addPackLineageField);
        addPackLineageField.setBounds(110, 480, 270, 35);

        addPackBreederField.setBackground(new java.awt.Color(237, 237, 237));
        addPackBreederField.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        addPackBreederField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        addPackBreederField.setBorder(null);
        addPackPanel.add(addPackBreederField);
        addPackBreederField.setBounds(180, 410, 190, 25);

        addPackReleaseField.setBackground(new java.awt.Color(204, 204, 204));
        addPackReleaseField.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        addPackReleaseField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        addPackReleaseField.setBorder(null);
        addPackPanel.add(addPackReleaseField);
        addPackReleaseField.setBounds(250, 440, 130, 25);

        addPackStockField.setBackground(new java.awt.Color(204, 204, 204));
        addPackStockField.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        addPackStockField.setBorder(null);
        addPackPanel.add(addPackStockField);
        addPackStockField.setBounds(100, 440, 50, 25);

        backToPackDisplayLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backToPackDisplayLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                backToPackDisplayLabelMouseReleased(evt);
            }
        });
        addPackPanel.add(backToPackDisplayLabel);
        backToPackDisplayLabel.setBounds(295, 825, 75, 55);

        submitPackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        submitPackButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                submitPackButtonMouseReleased(evt);
            }
        });
        addPackPanel.add(submitPackButton);
        submitPackButton.setBounds(20, 825, 75, 55);

        seedPackDisplayBackground1.setBackground(new java.awt.Color(232, 232, 232));
        seedPackDisplayBackground1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/seedbank/addPackPanelGraphic.png"))); // NOI18N
        seedPackDisplayBackground1.setToolTipText("");
        addPackPanel.add(seedPackDisplayBackground1);
        seedPackDisplayBackground1.setBounds(0, 0, 390, 890);

        seedBankSidePanel.add(addPackPanel);
        addPackPanel.setBounds(0, 0, 390, 890);

        seedPackDisplayPanel.setBackground(new java.awt.Color(255, 255, 255));
        seedPackDisplayPanel.setOpaque(false);
        seedPackDisplayPanel.setLayout(null);

        packCultivarLabel.setFont(new java.awt.Font("Mongolian Baiti", 0, 36)); // NOI18N
        packCultivarLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        packCultivarLabel.setText("Sol Sonic");
        seedPackDisplayPanel.add(packCultivarLabel);
        packCultivarLabel.setBounds(20, 367, 350, 40);

        packImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/seedbank/cultivars/default.png"))); // NOI18N
        seedPackDisplayPanel.add(packImageLabel);
        packImageLabel.setBounds(6, 5, 380, 365);

        packYieldLabel.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        packYieldLabel.setForeground(new java.awt.Color(51, 51, 51));
        packYieldLabel.setText("Heavy");
        packYieldLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        seedPackDisplayPanel.add(packYieldLabel);
        packYieldLabel.setBounds(245, 650, 130, 35);

        packSizeLabel.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        packSizeLabel.setForeground(new java.awt.Color(51, 51, 51));
        packSizeLabel.setText("Large Bush");
        seedPackDisplayPanel.add(packSizeLabel);
        packSizeLabel.setBounds(55, 650, 130, 35);

        packTerpeneLabel.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        packTerpeneLabel.setForeground(new java.awt.Color(51, 51, 51));
        packTerpeneLabel.setText("Cocoa n'Grease");
        seedPackDisplayPanel.add(packTerpeneLabel);
        packTerpeneLabel.setBounds(150, 605, 230, 40);

        packFloweringTimeLabel.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        packFloweringTimeLabel.setForeground(new java.awt.Color(51, 51, 51));
        packFloweringTimeLabel.setText("56-63 Days");
        seedPackDisplayPanel.add(packFloweringTimeLabel);
        packFloweringTimeLabel.setBounds(150, 565, 230, 35);

        packSeedTypeLabel.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        packSeedTypeLabel.setForeground(new java.awt.Color(51, 51, 51));
        packSeedTypeLabel.setText("Femenized Photoperiod (F)");
        seedPackDisplayPanel.add(packSeedTypeLabel);
        packSeedTypeLabel.setBounds(100, 520, 280, 40);

        packLineageLabel.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        packLineageLabel.setForeground(new java.awt.Color(51, 51, 51));
        packLineageLabel.setText("(Gary x Mind Flayer)");
        seedPackDisplayPanel.add(packLineageLabel);
        packLineageLabel.setBounds(80, 480, 300, 40);

        packReleaseLabel.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        packReleaseLabel.setText("Mind Flayer");
        seedPackDisplayPanel.add(packReleaseLabel);
        packReleaseLabel.setBounds(250, 440, 130, 25);

        packBreederLabel.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        packBreederLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        packBreederLabel.setText("Solfire Gardens");
        seedPackDisplayPanel.add(packBreederLabel);
        packBreederLabel.setBounds(180, 410, 190, 25);

        seedStockLabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        seedStockLabel.setText("6");
        seedPackDisplayPanel.add(seedStockLabel);
        seedStockLabel.setBounds(103, 440, 30, 25);

        packIdLabel.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        packIdLabel.setText("2133");
        seedPackDisplayPanel.add(packIdLabel);
        packIdLabel.setBounds(100, 410, 50, 25);

        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new java.awt.Color(204, 204, 204));
        descriptionArea.setColumns(20);
        descriptionArea.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        descriptionArea.setLineWrap(true);
        descriptionArea.setRows(5);
        descriptionArea.setText("Sol Sonic checks all the boxes. Expect blacked out large yielding bushes that are greasy to the touch. The Mind Flayer adds girth, structure, and color while the Gary infuses the gassy loud flavor you can taste from start to finsh.\n");
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setOpaque(false);
        seedPackDisplayPanel.add(descriptionArea);
        descriptionArea.setBounds(5, 690, 380, 120);

        seedPackDisplayBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/seedbank/displayPanelGraphic2.png"))); // NOI18N
        seedPackDisplayBackground.setToolTipText("");
        seedPackDisplayPanel.add(seedPackDisplayBackground);
        seedPackDisplayBackground.setBounds(0, 0, 390, 890);

        seedBankSidePanel.add(seedPackDisplayPanel);
        seedPackDisplayPanel.setBounds(0, 0, 390, 890);

        seedbankPanel.add(seedBankSidePanel);
        seedBankSidePanel.setBounds(760, 10, 390, 890);

        contentLayeredPane.add(seedbankPanel);
        seedbankPanel.setBounds(0, 0, 1160, 915);

        phenotypesPanel.setBackground(new java.awt.Color(42, 46, 45));
        phenotypesPanel.setLayout(null);

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("This is the Phenotypes");
        phenotypesPanel.add(jLabel9);
        jLabel9.setBounds(409, 0, 171, 53);

        contentLayeredPane.add(phenotypesPanel);
        phenotypesPanel.setBounds(0, 0, 1160, 915);

        clonesPanel.setBackground(new java.awt.Color(42, 46, 45));
        clonesPanel.setLayout(null);

        CloneMachine2Panel.setBackground(new java.awt.Color(153, 153, 153));
        CloneMachine2Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cloneUIPanel1.setOpaque(false);

        CloneMachine2Slot1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CloneMachine2Slot1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot1.setOpaque(true);
        CloneMachine2Slot1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot1MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot1MouseReleased(evt);
            }
        });

        CloneMachine2Slot8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot8.setOpaque(true);
        CloneMachine2Slot8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot8MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot8MouseReleased(evt);
            }
        });

        CloneMachine2Slot15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot15.setOpaque(true);
        CloneMachine2Slot15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot15MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot15MouseReleased(evt);
            }
        });

        CloneMachine2Slot22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot22.setOpaque(true);
        CloneMachine2Slot22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot22MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot22MouseReleased(evt);
            }
        });

        CloneMachine2Slot29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot29.setOpaque(true);
        CloneMachine2Slot29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot29MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot29MouseReleased(evt);
            }
        });

        CloneMachine2Slot7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot7.setOpaque(true);
        CloneMachine2Slot7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot7MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot7MouseReleased(evt);
            }
        });

        CloneMachine2Slot2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CloneMachine2Slot2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot2.setOpaque(true);
        CloneMachine2Slot2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot2MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot2MouseReleased(evt);
            }
        });

        CloneMachine2Slot9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot9.setOpaque(true);
        CloneMachine2Slot9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot9MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot9MouseReleased(evt);
            }
        });

        CloneMachine2Slot16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot16.setOpaque(true);
        CloneMachine2Slot16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot16MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot16MouseReleased(evt);
            }
        });

        CloneMachine2Slot23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot23.setOpaque(true);
        CloneMachine2Slot23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot23MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot23MouseReleased(evt);
            }
        });

        CloneMachine2Slot30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot30.setOpaque(true);
        CloneMachine2Slot30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot30MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot30MouseReleased(evt);
            }
        });

        CloneMachine2Slot14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot14.setOpaque(true);
        CloneMachine2Slot14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot14MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot14MouseReleased(evt);
            }
        });

        CloneMachine2Slot3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot3.setOpaque(true);
        CloneMachine2Slot3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot3MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot3MouseReleased(evt);
            }
        });

        CloneMachine2Slot10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot10.setOpaque(true);
        CloneMachine2Slot10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot10MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot10MouseReleased(evt);
            }
        });

        CloneMachine2Slot17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot17.setOpaque(true);
        CloneMachine2Slot17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot17MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot17MouseReleased(evt);
            }
        });

        CloneMachine2Slot24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot24.setOpaque(true);
        CloneMachine2Slot24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot24MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot24MouseReleased(evt);
            }
        });

        CloneMachine2Slot31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot31.setOpaque(true);
        CloneMachine2Slot31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot31MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot31MouseReleased(evt);
            }
        });

        CloneMachine2Slot21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot21.setOpaque(true);
        CloneMachine2Slot21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot21MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot21MouseReleased(evt);
            }
        });

        CloneMachine2Slot4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot4.setOpaque(true);
        CloneMachine2Slot4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot4MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot4MouseReleased(evt);
            }
        });

        CloneMachine2Slot11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot11.setOpaque(true);
        CloneMachine2Slot11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot11MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot11MouseReleased(evt);
            }
        });

        CloneMachine2Slot18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot18.setOpaque(true);
        CloneMachine2Slot18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot18MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot18MouseReleased(evt);
            }
        });

        CloneMachine2Slot25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot25.setOpaque(true);
        CloneMachine2Slot25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot25MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot25MouseReleased(evt);
            }
        });

        CloneMachine2Slot32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot32.setOpaque(true);
        CloneMachine2Slot32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot32MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot32MouseReleased(evt);
            }
        });

        CloneMachine2Slot28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot28.setOpaque(true);
        CloneMachine2Slot28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot28MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot28MouseReleased(evt);
            }
        });

        CloneMachine2Slot5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot5.setOpaque(true);
        CloneMachine2Slot5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot5MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot5MouseReleased(evt);
            }
        });

        CloneMachine2Slot12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot12.setOpaque(true);
        CloneMachine2Slot12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot12MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot12MouseReleased(evt);
            }
        });

        CloneMachine2Slot19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot19.setOpaque(true);
        CloneMachine2Slot19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot19MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot19MouseReleased(evt);
            }
        });

        CloneMachine2Slot26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot26.setOpaque(true);
        CloneMachine2Slot26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot26MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot26MouseReleased(evt);
            }
        });

        CloneMachine2Slot33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot33.setOpaque(true);
        CloneMachine2Slot33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot33MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot33MouseReleased(evt);
            }
        });

        CloneMachine2Slot35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot35.setOpaque(true);
        CloneMachine2Slot35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot35MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot35MouseReleased(evt);
            }
        });

        CloneMachine2Slot6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot6.setName("F1"); // NOI18N
        CloneMachine2Slot6.setOpaque(true);
        CloneMachine2Slot6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot6MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot6MouseReleased(evt);
            }
        });

        CloneMachine2Slot13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot13.setOpaque(true);
        CloneMachine2Slot13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot13MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot13MouseReleased(evt);
            }
        });

        CloneMachine2Slot20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot20.setOpaque(true);
        CloneMachine2Slot20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot20MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot20MouseReleased(evt);
            }
        });

        CloneMachine2Slot27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot27.setOpaque(true);
        CloneMachine2Slot27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot27MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot27MouseReleased(evt);
            }
        });

        CloneMachine2Slot34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CloneMachine2Slot34.setOpaque(true);
        CloneMachine2Slot34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot34MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CloneMachine2Slot34MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout cloneUIPanel1Layout = new javax.swing.GroupLayout(cloneUIPanel1);
        cloneUIPanel1.setLayout(cloneUIPanel1Layout);
        cloneUIPanel1Layout.setHorizontalGroup(
            cloneUIPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cloneUIPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(cloneUIPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cloneUIPanel1Layout.createSequentialGroup()
                        .addComponent(CloneMachine2Slot1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(CloneMachine2Slot4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(CloneMachine2Slot5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cloneUIPanel1Layout.createSequentialGroup()
                        .addComponent(CloneMachine2Slot8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cloneUIPanel1Layout.createSequentialGroup()
                        .addComponent(CloneMachine2Slot15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot20, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cloneUIPanel1Layout.createSequentialGroup()
                        .addComponent(CloneMachine2Slot22, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot24, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot26, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot27, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot28, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cloneUIPanel1Layout.createSequentialGroup()
                        .addComponent(CloneMachine2Slot29, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot30, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot31, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot32, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot33, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot34, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CloneMachine2Slot35, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        cloneUIPanel1Layout.setVerticalGroup(
            cloneUIPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cloneUIPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(cloneUIPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CloneMachine2Slot1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(cloneUIPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(CloneMachine2Slot4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(CloneMachine2Slot5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(cloneUIPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CloneMachine2Slot8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cloneUIPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CloneMachine2Slot15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot20, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cloneUIPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CloneMachine2Slot22, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot24, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot26, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot27, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot28, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cloneUIPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CloneMachine2Slot29, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot30, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot31, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot32, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot33, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot34, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloneMachine2Slot35, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CloneMachine2Panel.add(cloneUIPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, 476));

        cloneMachineSidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        emptyCuttingSlotPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setText("Transplant");
        jPanel3.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 10, 175, 130));

        viewCuttingMotherButton1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        viewCuttingMotherButton1.setText("Take Cuttings");
        viewCuttingMotherButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                viewCuttingMotherButton1MouseReleased(evt);
            }
        });
        jPanel3.add(viewCuttingMotherButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 175, 130));

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("Log Notes");
        jPanel3.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 175, 130));

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setText("Remove Cuttings");
        jPanel3.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 130, 175, 130));

        emptyCuttingSlotPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 380, 270));

        cloneMachineSidePanel.add(emptyCuttingSlotPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 570));

        selectedCuttingPanel.setEnabled(false);
        selectedCuttingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Plant Image");
        selectedCuttingPanel.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 238, 169));

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("Cut Date");
        selectedCuttingPanel.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 250, 150, -1));

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Mother ID");
        selectedCuttingPanel.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 230, 150, -1));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Cultivar Label");
        selectedCuttingPanel.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 190, 170, 40));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("Transplant");
        jPanel2.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 10, 175, 130));

        viewCuttingMotherButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        viewCuttingMotherButton.setText("View Mother");
        jPanel2.add(viewCuttingMotherButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 175, 130));

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("Log Notes");
        jPanel2.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 175, 130));

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("Remove Cutting");
        jPanel2.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 130, 175, 130));

        selectedCuttingPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 380, 270));

        cloneMachineSidePanel.add(selectedCuttingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 570));

        CloneMachine2Panel.add(cloneMachineSidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(706, 319, -1, -1));

        javax.swing.GroupLayout cloneMachineDashboardPanelLayout = new javax.swing.GroupLayout(cloneMachineDashboardPanel);
        cloneMachineDashboardPanel.setLayout(cloneMachineDashboardPanelLayout);
        cloneMachineDashboardPanelLayout.setHorizontalGroup(
            cloneMachineDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1136, Short.MAX_VALUE)
        );
        cloneMachineDashboardPanelLayout.setVerticalGroup(
            cloneMachineDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        CloneMachine2Panel.add(cloneMachineDashboardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 1136, -1));

        clonesPanel.add(CloneMachine2Panel);
        CloneMachine2Panel.setBounds(0, 0, 1160, 910);

        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Pheno Finder");
        jLabel40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel40MouseReleased(evt);
            }
        });
        clonesPanel.add(jLabel40);
        jLabel40.setBounds(970, 650, 100, 16);

        jLabel54.setBackground(new java.awt.Color(255, 255, 255));
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Indoor/wepik-20211014-02125.png"))); // NOI18N
        clonesPanel.add(jLabel54);
        jLabel54.setBounds(0, 0, 1160, 915);

        cloneEventPanel.setDoubleBuffered(false);
        cloneEventPanel.setEnabled(false);
        cloneEventPanel.setOpaque(false);
        cloneEventPanel.setPreferredSize(new java.awt.Dimension(372, 400));
        cloneEventPanel.setLayout(null);

        cloneEventPlantTag.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        cloneEventPlantTag.setForeground(new java.awt.Color(255, 255, 255));
        cloneEventPlantTag.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cloneEventPanel.add(cloneEventPlantTag);
        cloneEventPlantTag.setBounds(140, 40, 90, 20);

        cloneEventPlantId.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        cloneEventPlantId.setForeground(new java.awt.Color(255, 255, 255));
        cloneEventPlantId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cloneEventPanel.add(cloneEventPlantId);
        cloneEventPlantId.setBounds(160, 70, 50, 10);

        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Exit");
        jLabel59.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel59.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel59MouseReleased(evt);
            }
        });
        cloneEventPanel.add(jLabel59);
        jLabel59.setBounds(260, 370, 90, 16);

        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("Title");
        cloneEventPanel.add(jLabel58);
        jLabel58.setBounds(90, 100, 190, 16);

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("Reset");
        jLabel62.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel62MouseReleased(evt);
            }
        });
        cloneEventPanel.add(jLabel62);
        jLabel62.setBounds(160, 360, 50, 13);

        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("Submit");
        jLabel60.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel60MouseReleased(evt);
            }
        });
        cloneEventPanel.add(jLabel60);
        jLabel60.setBounds(30, 370, 80, 16);

        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setText("Event");
        cloneEventPanel.add(jLabel61);
        jLabel61.setBounds(90, 170, 190, 30);

        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("Clone Event");
        cloneEventPanel.add(jLabel57);
        jLabel57.setBounds(90, 10, 190, 16);

        jTextField1.setBackground(new java.awt.Color(0, 0, 0));
        jTextField1.setFont(new java.awt.Font("Microsoft JhengHei", 0, 10)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cloneEventPanel.add(jTextField1);
        jTextField1.setBounds(85, 130, 200, 16);

        cloneEventTextArea.setBackground(new java.awt.Color(0, 0, 0));
        cloneEventTextArea.setColumns(20);
        cloneEventTextArea.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        cloneEventTextArea.setForeground(new java.awt.Color(255, 255, 255));
        cloneEventTextArea.setRows(5);
        cloneEventTextArea.setWrapStyleWord(true);
        cloneEventTextArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        cloneEventPanel.add(cloneEventTextArea);
        cloneEventTextArea.setBounds(30, 200, 310, 160);
        cloneEventTextArea.setLineWrap(true);
        cloneEventTextArea.setWrapStyleWord(true);

        cloneEventBackground.setBackground(new java.awt.Color(0, 0, 0));
        cloneEventBackground.setOpaque(true);
        cloneEventPanel.add(cloneEventBackground);
        cloneEventBackground.setBounds(0, 0, 372, 400);
        editSlotBackground.setBackground(new Color(0, 0, 0, 220));

        clonesPanel.add(cloneEventPanel);
        cloneEventPanel.setBounds(399, 253, 370, 400);

        supportPanel.setBackground(new java.awt.Color(0, 0, 0));
        supportPanel.setOpaque(false);
        supportPanel.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("F");
        supportPanel.add(jLabel10);
        jLabel10.setBounds(580, 50, 80, 50);

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("B");
        supportPanel.add(jLabel12);
        jLabel12.setBounds(190, 50, 80, 50);

        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("C");
        supportPanel.add(jLabel13);
        jLabel13.setBounds(290, 50, 80, 50);

        jLabel14.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("D");
        supportPanel.add(jLabel14);
        jLabel14.setBounds(390, 50, 70, 50);

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("E");
        supportPanel.add(jLabel15);
        jLabel15.setBounds(480, 50, 80, 50);

        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("A");
        supportPanel.add(jLabel19);
        jLabel19.setBounds(90, 50, 80, 50);

        jLabel21.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("1");
        supportPanel.add(jLabel21);
        jLabel21.setBounds(30, 110, 60, 80);

        jLabel22.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("2");
        supportPanel.add(jLabel22);
        jLabel22.setBounds(30, 210, 60, 80);

        jLabel23.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("3");
        supportPanel.add(jLabel23);
        jLabel23.setBounds(30, 310, 60, 80);

        jLabel24.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("4");
        supportPanel.add(jLabel24);
        jLabel24.setBounds(30, 410, 60, 80);

        jLabel25.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("5");
        supportPanel.add(jLabel25);
        jLabel25.setBounds(30, 500, 60, 80);

        jLabel20.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("6");
        supportPanel.add(jLabel20);
        jLabel20.setBounds(30, 600, 60, 80);

        clonesPanel.add(supportPanel);
        supportPanel.setBounds(210, 110, 730, 700);
        supportPanel.setBackground(new Color(0, 0, 0, 255));
        this.setVisible(false);

        cloneStartupPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cloneMachineSlot4.setBackground(new java.awt.Color(153, 153, 153));
        cloneMachineSlot4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cloneStartupPanel.add(cloneMachineSlot4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 360, 240));

        cloneMachineSlot6.setBackground(new java.awt.Color(153, 153, 153));
        cloneMachineSlot6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cloneStartupPanel.add(cloneMachineSlot6, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 380, 360, 240));

        cloneMachineSlot5.setBackground(new java.awt.Color(153, 153, 153));
        cloneMachineSlot5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cloneStartupPanel.add(cloneMachineSlot5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 380, 360, 240));

        cloneMachineSlot3.setBackground(new java.awt.Color(153, 153, 153));
        cloneMachineSlot3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cloneStartupPanel.add(cloneMachineSlot3, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, 360, 240));

        cloneMachineSlot1.setBackground(new java.awt.Color(153, 153, 153));
        cloneMachineSlot1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Select Cloner");
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel39MouseReleased(evt);
            }
        });
        cloneMachineSlot1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 150, 110));

        cloneStartupPanel.add(cloneMachineSlot1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 360, 240));

        cloneMachineSlot2.setBackground(new java.awt.Color(153, 153, 153));
        cloneMachineSlot2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cloneStartupPanel.add(cloneMachineSlot2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 110, 360, 240));

        cloneMachineSlot9.setBackground(new java.awt.Color(153, 153, 153));
        cloneMachineSlot9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cloneStartupPanel.add(cloneMachineSlot9, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 660, 360, 240));

        cloneMachineSlot8.setBackground(new java.awt.Color(153, 153, 153));
        cloneMachineSlot8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cloneStartupPanel.add(cloneMachineSlot8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 660, 360, 240));

        cloneMachineSlot7.setBackground(new java.awt.Color(153, 153, 153));
        cloneMachineSlot7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cloneStartupPanel.add(cloneMachineSlot7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 660, 360, 240));

        clonesPanel.add(cloneStartupPanel);
        cloneStartupPanel.setBounds(0, 0, 1160, 915);

        cloneSelectionPanel.setOpaque(false);
        cloneSelectionPanel.setLayout(null);

        cloneSelectionPanelSlotLabel.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        cloneSelectionPanelSlotLabel.setForeground(new java.awt.Color(255, 255, 255));
        cloneSelectionPanelSlotLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cloneSelectionPanelSlotLabel.setText("Machine Slot");
        cloneSelectionPanel.add(cloneSelectionPanelSlotLabel);
        cloneSelectionPanelSlotLabel.setBounds(10, 10, 170, 23);

        cloneSelectionPanelPlantIdText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cloneSelectionPanel.add(cloneSelectionPanelPlantIdText);
        cloneSelectionPanelPlantIdText.setBounds(80, 80, 100, 16);

        cloneSelectionPanelMotherIdText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cloneSelectionPanel.add(cloneSelectionPanelMotherIdText);
        cloneSelectionPanelMotherIdText.setBounds(0, 30, 190, 16);

        cloneSelectionPanelCutDateText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cloneSelectionPanel.add(cloneSelectionPanelCutDateText);
        cloneSelectionPanelCutDateText.setBounds(80, 140, 100, 16);

        cloneSelectionPanelStrain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cloneSelectionPanel.add(cloneSelectionPanelStrain);
        cloneSelectionPanelStrain.setBounds(90, 50, 90, 20);

        cloneSelectionPlantIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cloneSelectionPlantIdLabel.setText("Plant Id:");
        cloneSelectionPanel.add(cloneSelectionPlantIdLabel);
        cloneSelectionPlantIdLabel.setBounds(0, 80, 90, 16);

        cloneSelectionMotherPlantIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cloneSelectionMotherPlantIdLabel.setText("Mother Id:");
        cloneSelectionPanel.add(cloneSelectionMotherPlantIdLabel);
        cloneSelectionMotherPlantIdLabel.setBounds(0, 110, 90, 16);

        cloneSelectionCutDateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cloneSelectionCutDateLabel.setText("Cut Date");
        cloneSelectionPanel.add(cloneSelectionCutDateLabel);
        cloneSelectionCutDateLabel.setBounds(0, 140, 90, 16);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Cloning/cloneEventFlag.png"))); // NOI18N
        jLabel56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel56MouseReleased(evt);
            }
        });
        cloneSelectionPanel.add(jLabel56);
        jLabel56.setBounds(70, 190, 30, 30);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Cloning/cloneEventFlag.png"))); // NOI18N
        cloneSelectionPanel.add(jLabel45);
        jLabel45.setBounds(20, 190, 30, 30);
        cloneSelectionPanel.add(jLabel46);
        jLabel46.setBounds(65, 190, 30, 30);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Cloning/cloneTrashCan.png"))); // NOI18N
        cloneSelectionPanel.add(jLabel47);
        jLabel47.setBounds(125, 190, 30, 30);
        cloneSelectionPanel.add(jLabel48);
        jLabel48.setBounds(155, 190, 30, 30);
        cloneSelectionPanel.add(jLabel49);
        jLabel49.setBounds(95, 190, 30, 30);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Strain:");
        cloneSelectionPanel.add(jLabel55);
        jLabel55.setBounds(20, 50, 50, 16);

        cloneSelectionPanelBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Cloning/cloneSlotSelectionPanel.png"))); // NOI18N
        cloneSelectionPanel.add(cloneSelectionPanelBG);
        cloneSelectionPanelBG.setBounds(0, 0, 190, 220);

        clonesPanel.add(cloneSelectionPanel);
        cloneSelectionPanel.setBounds(930, 400, 190, 220);

        phenoFinderPanel.setBackground(new java.awt.Color(42, 46, 45));
        phenoFinderPanel.setOpaque(false);
        phenoFinderPanel.setPreferredSize(new java.awt.Dimension(520, 630));
        phenoFinderPanel.setLayout(null);

        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Exit");
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel41MouseReleased(evt);
            }
        });
        phenoFinderPanel.add(jLabel41);
        jLabel41.setBounds(430, 10, 60, 30);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Pheno Finder");
        phenoFinderPanel.add(jLabel17);
        jLabel17.setBounds(229, 0, 61, 13);
        phenoFinderPanel.add(jLabel18);
        jLabel18.setBounds(165, 20, 190, 150);

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Enter Plant ID, Location, Strain");
        phenoFinderPanel.add(jLabel32);
        jLabel32.setBounds(160, 180, 190, 13);

        searchField.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        searchField.setForeground(new java.awt.Color(255, 255, 255));
        searchField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        phenoFinderPanel.add(searchField);
        searchField.setBounds(190, 200, 130, 20);
        searchField.setBackground(new Color( 42,46,45, 0));

        phenoFinderSearchLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        phenoFinderSearchLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/searchIcon16.png"))); // NOI18N
        phenoFinderSearchLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        phenoFinderSearchLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                phenoFinderSearchLabelMouseReleased(evt);
            }
        });
        phenoFinderPanel.add(phenoFinderSearchLabel);
        phenoFinderSearchLabel.setBounds(240, 225, 25, 25);

        tablePanel.setBackground(new java.awt.Color(42, 46, 45));
        tablePanel.setOpaque(false);
        tablePanel.setLayout(null);

        idTitleLabel.setBackground(new java.awt.Color(255, 255, 255));
        idTitleLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        idTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idTitleLabel.setText("Plant ID");
        idTitleLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idTitleLabel);
        idTitleLabel.setBounds(5, 0, 163, 30);

        idCell1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idCell1.setForeground(new java.awt.Color(255, 255, 255));
        idCell1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idCell1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idCell1);
        idCell1.setBounds(5, 30, 163, 30);

        idCell2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idCell2.setForeground(new java.awt.Color(255, 255, 255));
        idCell2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idCell2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idCell2);
        idCell2.setBounds(5, 60, 163, 30);

        idCell3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idCell3.setForeground(new java.awt.Color(255, 255, 255));
        idCell3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idCell3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idCell3);
        idCell3.setBounds(5, 90, 163, 30);

        idCell4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idCell4.setForeground(new java.awt.Color(255, 255, 255));
        idCell4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idCell4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idCell4);
        idCell4.setBounds(5, 120, 163, 30);

        idCell5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idCell5.setForeground(new java.awt.Color(255, 255, 255));
        idCell5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idCell5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idCell5);
        idCell5.setBounds(5, 150, 163, 30);

        idCell6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idCell6.setForeground(new java.awt.Color(255, 255, 255));
        idCell6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idCell6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idCell6);
        idCell6.setBounds(5, 180, 163, 30);

        idCell7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idCell7.setForeground(new java.awt.Color(255, 255, 255));
        idCell7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idCell7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idCell7);
        idCell7.setBounds(5, 210, 163, 30);

        idCell8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idCell8.setForeground(new java.awt.Color(255, 255, 255));
        idCell8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idCell8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idCell8);
        idCell8.setBounds(5, 240, 163, 30);

        idCell9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idCell9.setForeground(new java.awt.Color(255, 255, 255));
        idCell9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idCell9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idCell9);
        idCell9.setBounds(5, 270, 163, 30);

        idCell10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idCell10.setForeground(new java.awt.Color(255, 255, 255));
        idCell10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idCell10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(idCell10);
        idCell10.setBounds(5, 300, 163, 30);

        strainCell1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainCell1.setForeground(new java.awt.Color(255, 255, 255));
        strainCell1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainCell1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainCell1);
        strainCell1.setBounds(173, 30, 180, 30);

        strainCell2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainCell2.setForeground(new java.awt.Color(255, 255, 255));
        strainCell2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainCell2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainCell2);
        strainCell2.setBounds(173, 60, 180, 30);

        strainCell3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainCell3.setForeground(new java.awt.Color(255, 255, 255));
        strainCell3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainCell3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainCell3);
        strainCell3.setBounds(173, 90, 180, 30);

        strainCell4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainCell4.setForeground(new java.awt.Color(255, 255, 255));
        strainCell4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainCell4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainCell4);
        strainCell4.setBounds(173, 120, 180, 30);

        strainCell5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainCell5.setForeground(new java.awt.Color(255, 255, 255));
        strainCell5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainCell5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainCell5);
        strainCell5.setBounds(173, 150, 180, 30);

        strainCell6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainCell6.setForeground(new java.awt.Color(255, 255, 255));
        strainCell6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainCell6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainCell6);
        strainCell6.setBounds(173, 180, 180, 30);

        strainCell7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainCell7.setForeground(new java.awt.Color(255, 255, 255));
        strainCell7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainCell7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainCell7);
        strainCell7.setBounds(173, 210, 180, 30);

        strainCell8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainCell8.setForeground(new java.awt.Color(255, 255, 255));
        strainCell8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainCell8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainCell8);
        strainCell8.setBounds(173, 240, 180, 30);

        strainCell9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainCell9.setForeground(new java.awt.Color(255, 255, 255));
        strainCell9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainCell9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainCell9);
        strainCell9.setBounds(173, 270, 180, 30);

        strainCell10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainCell10.setForeground(new java.awt.Color(255, 255, 255));
        strainCell10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainCell10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainCell10);
        strainCell10.setBounds(173, 300, 180, 30);

        locationCell1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationCell1.setForeground(new java.awt.Color(255, 255, 255));
        locationCell1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationCell1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationCell1);
        locationCell1.setBounds(357, 30, 158, 30);

        locationCell2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationCell2.setForeground(new java.awt.Color(255, 255, 255));
        locationCell2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationCell2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationCell2);
        locationCell2.setBounds(357, 60, 158, 30);

        locationCell3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationCell3.setForeground(new java.awt.Color(255, 255, 255));
        locationCell3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationCell3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationCell3);
        locationCell3.setBounds(357, 90, 158, 30);

        locationCell4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationCell4.setForeground(new java.awt.Color(255, 255, 255));
        locationCell4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationCell4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationCell4);
        locationCell4.setBounds(357, 120, 158, 30);

        locationCell5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationCell5.setForeground(new java.awt.Color(255, 255, 255));
        locationCell5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationCell5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationCell5);
        locationCell5.setBounds(357, 150, 158, 30);

        locationCell6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationCell6.setForeground(new java.awt.Color(255, 255, 255));
        locationCell6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationCell6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationCell6);
        locationCell6.setBounds(357, 180, 158, 30);

        locationCell7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationCell7.setForeground(new java.awt.Color(255, 255, 255));
        locationCell7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationCell7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationCell7);
        locationCell7.setBounds(357, 210, 158, 30);

        locationCell8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationCell8.setForeground(new java.awt.Color(255, 255, 255));
        locationCell8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationCell8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationCell8);
        locationCell8.setBounds(357, 240, 158, 30);

        locationCell9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationCell9.setForeground(new java.awt.Color(255, 255, 255));
        locationCell9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationCell9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationCell9);
        locationCell9.setBounds(357, 270, 158, 30);

        locationCell10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationCell10.setForeground(new java.awt.Color(255, 255, 255));
        locationCell10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationCell10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationCell10);
        locationCell10.setBounds(357, 300, 158, 30);

        locationTitleLab.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationTitleLab.setForeground(new java.awt.Color(255, 255, 255));
        locationTitleLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationTitleLab.setText("Plant Location");
        locationTitleLab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(locationTitleLab);
        locationTitleLab.setBounds(357, 0, 158, 30);

        strainTitleLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        strainTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strainTitleLabel.setText("Plant Strain");
        strainTitleLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablePanel.add(strainTitleLabel);
        strainTitleLabel.setBounds(173, 0, 180, 30);

        phenoFinderPanel.add(tablePanel);
        tablePanel.setBounds(0, 270, 520, 330);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 255, 51));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("test");
        phenoFinderPanel.add(jLabel33);
        jLabel33.setBounds(188, 257, 130, 10);

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Map/phenoFinder.png"))); // NOI18N
        phenoFinderPanel.add(jLabel36);
        jLabel36.setBounds(0, 0, 520, 610);

        clonesPanel.add(phenoFinderPanel);
        phenoFinderPanel.setBounds(320, 200, 520, 610);

        editSlotPanel.setBackground(new java.awt.Color(0, 0, 0));
        editSlotPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        editSlotPanel.setPreferredSize(new java.awt.Dimension(372, 600));
        editSlotPanel.setLayout(null);

        searchIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/UI Icons/Cloning/searchIcon.png"))); // NOI18N
        searchIconLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        searchIconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                searchIconLabelMouseReleased(evt);
            }
        });
        editSlotPanel.add(searchIconLabel);
        searchIconLabel.setBounds(160, 80, 50, 50);

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Exit");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel16MouseReleased(evt);
            }
        });
        editSlotPanel.add(jLabel16);
        jLabel16.setBounds(218, 394, 80, 50);

        addCloneLabel.setForeground(new java.awt.Color(255, 255, 255));
        addCloneLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addCloneLabel.setText("Take Cutting");
        addCloneLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addCloneLabelMouseReleased(evt);
            }
        });
        editSlotPanel.add(addCloneLabel);
        addCloneLabel.setBounds(50, 390, 90, 50);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Enter Mother Plant Id");
        editSlotPanel.add(jLabel11);
        jLabel11.setBounds(0, 10, 370, 20);

        strainLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        strainLabel.setForeground(new java.awt.Color(255, 255, 255));
        strainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editSlotPanel.add(strainLabel);
        strainLabel.setBounds(100, 260, 170, 20);

        locationLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        locationLabel.setForeground(new java.awt.Color(255, 255, 255));
        locationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editSlotPanel.add(locationLabel);
        locationLabel.setBounds(100, 340, 170, 20);

        idLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        idLabel.setForeground(new java.awt.Color(255, 255, 255));
        idLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editSlotPanel.add(idLabel);
        idLabel.setBounds(100, 200, 170, 20);

        nLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nLabel2.setForeground(new java.awt.Color(255, 255, 255));
        nLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nLabel2.setText("strain");
        editSlotPanel.add(nLabel2);
        nLabel2.setBounds(140, 230, 90, 20);

        nLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nLabel1.setForeground(new java.awt.Color(255, 255, 255));
        nLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        nLabel1.setText("location");
        editSlotPanel.add(nLabel1);
        nLabel1.setBounds(160, 300, 50, 20);

        nLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nLabel.setForeground(new java.awt.Color(255, 255, 255));
        nLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nLabel.setText("ID");
        editSlotPanel.add(nLabel);
        nLabel.setBounds(165, 170, 40, 20);

        searchFeedbackLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        searchFeedbackLabel.setForeground(new java.awt.Color(204, 204, 204));
        searchFeedbackLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchFeedbackLabel.setText("Load Pheno");
        editSlotPanel.add(searchFeedbackLabel);
        searchFeedbackLabel.setBounds(120, 140, 130, 13);

        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Pheno Finder");
        jLabel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel42MouseReleased(evt);
            }
        });
        editSlotPanel.add(jLabel42);
        jLabel42.setBounds(150, 460, 70, 16);

        motherIdTextField.setBackground(new java.awt.Color(0, 0, 0));
        motherIdTextField.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        motherIdTextField.setForeground(new java.awt.Color(255, 255, 255));
        motherIdTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        motherIdTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        editSlotPanel.add(motherIdTextField);
        motherIdTextField.setBounds(85, 40, 200, 30);
        motherIdTextField.setBackground(new Color(0, 0, 0, 0));
        motherIdTextField.requestFocus();
        motherIdTextField.setCaretPosition(0);

        editSlotBackground.setBackground(new java.awt.Color(0, 0, 0));
        editSlotBackground.setOpaque(true);
        editSlotPanel.add(editSlotBackground);
        editSlotBackground.setBounds(0, 0, 372, 460);
        editSlotBackground.setBackground(new Color(0, 0, 0));

        backgroundBlurLabel.setBackground(new java.awt.Color(0, 0, 0));
        backgroundBlurLabel.setOpaque(true);
        editSlotPanel.add(backgroundBlurLabel);
        backgroundBlurLabel.setBounds(0, 0, 370, 510);
        backgroundBlurLabel.setBackground(new Color(100, 100, 100, 230));

        clonesPanel.add(editSlotPanel);
        editSlotPanel.setBounds(399, 443, 372, 460);
        clonesPanel.setBackground(new Color(0, 0, 0));
        editSlotPanel.setVisible(false);

        cloneUIPanel.setOpaque(false);

        A1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        A1.setOpaque(true);
        A1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                A1MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                A1MouseReleased(evt);
            }
        });

        A2.setOpaque(true);
        A2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                A2MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                A2MouseReleased(evt);
            }
        });

        A3.setOpaque(true);
        A3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                A3MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                A3MouseReleased(evt);
            }
        });

        A4.setOpaque(true);
        A4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                A4MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                A4MouseReleased(evt);
            }
        });

        A5.setOpaque(true);
        A5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                A5MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                A5MouseReleased(evt);
            }
        });

        A6.setOpaque(true);
        A6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                A6MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                A6MouseReleased(evt);
            }
        });

        B1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        B1.setOpaque(true);
        B1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                B1MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                B1MouseReleased(evt);
            }
        });

        B2.setOpaque(true);
        B2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                B2MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                B2MouseReleased(evt);
            }
        });

        B3.setOpaque(true);
        B3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                B3MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                B3MouseReleased(evt);
            }
        });

        B4.setOpaque(true);
        B4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                B4MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                B4MouseReleased(evt);
            }
        });

        B5.setOpaque(true);
        B5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                B5MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                B5MouseReleased(evt);
            }
        });

        B6.setOpaque(true);
        B6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                B6MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                B6MouseReleased(evt);
            }
        });

        C1.setOpaque(true);
        C1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                C1MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                C1MouseReleased(evt);
            }
        });

        C2.setOpaque(true);
        C2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                C2MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                C2MouseReleased(evt);
            }
        });

        C3.setOpaque(true);
        C3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                C3MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                C3MouseReleased(evt);
            }
        });

        C4.setOpaque(true);
        C4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                C4MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                C4MouseReleased(evt);
            }
        });

        C5.setOpaque(true);
        C5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                C5MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                C5MouseReleased(evt);
            }
        });

        C6.setOpaque(true);
        C6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                C6MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                C6MouseReleased(evt);
            }
        });

        D1.setOpaque(true);
        D1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                D1MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                D1MouseReleased(evt);
            }
        });

        D2.setOpaque(true);
        D2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                D2MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                D2MouseReleased(evt);
            }
        });

        D3.setOpaque(true);
        D3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                D3MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                D3MouseReleased(evt);
            }
        });

        D4.setOpaque(true);
        D4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                D4MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                D4MouseReleased(evt);
            }
        });

        D5.setOpaque(true);
        D5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                D5MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                D5MouseReleased(evt);
            }
        });

        D6.setOpaque(true);
        D6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                D6MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                D6MouseReleased(evt);
            }
        });

        E1.setOpaque(true);
        E1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                E1MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                E1MouseReleased(evt);
            }
        });

        E2.setOpaque(true);
        E2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                E2MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                E2MouseReleased(evt);
            }
        });

        E3.setOpaque(true);
        E3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                E3MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                E3MouseReleased(evt);
            }
        });

        E4.setOpaque(true);
        E4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                E4MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                E4MouseReleased(evt);
            }
        });

        E5.setOpaque(true);
        E5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                E5MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                E5MouseReleased(evt);
            }
        });

        E6.setOpaque(true);
        E6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                E6MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                E6MouseReleased(evt);
            }
        });

        F1.setName("F1"); // NOI18N
        F1.setOpaque(true);
        F1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                F1MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                F1MouseReleased(evt);
            }
        });

        F2.setOpaque(true);
        F2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                F2MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                F2MouseReleased(evt);
            }
        });

        F3.setOpaque(true);
        F3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                F3MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                F3MouseReleased(evt);
            }
        });

        F4.setOpaque(true);
        F4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                F4MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                F4MouseReleased(evt);
            }
        });

        F5.setOpaque(true);
        F5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                F5MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                F5MouseReleased(evt);
            }
        });

        F6.setOpaque(true);
        F6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                F6MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                F6MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout cloneUIPanelLayout = new javax.swing.GroupLayout(cloneUIPanel);
        cloneUIPanel.setLayout(cloneUIPanelLayout);
        cloneUIPanelLayout.setHorizontalGroup(
            cloneUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cloneUIPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(cloneUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cloneUIPanelLayout.createSequentialGroup()
                        .addComponent(A1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(C1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(D1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(E1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(F1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cloneUIPanelLayout.createSequentialGroup()
                        .addComponent(A2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(C2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(D2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(E2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(F2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cloneUIPanelLayout.createSequentialGroup()
                        .addComponent(A3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(C3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(D3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(E3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(F3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cloneUIPanelLayout.createSequentialGroup()
                        .addComponent(A4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(C4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(D4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(E4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(F4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cloneUIPanelLayout.createSequentialGroup()
                        .addComponent(A5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(C5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(D5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(E5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(F5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cloneUIPanelLayout.createSequentialGroup()
                        .addComponent(A6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(C6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(D6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(E6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(F6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        cloneUIPanelLayout.setVerticalGroup(
            cloneUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cloneUIPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(cloneUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(A1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(cloneUIPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(D1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17)
                .addGroup(cloneUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(A2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cloneUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(A3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cloneUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(A4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cloneUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(A5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cloneUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(A6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        clonesPanel.add(cloneUIPanel);
        cloneUIPanel.setBounds(270, 210, 590, 590);

        contentLayeredPane.add(clonesPanel);
        clonesPanel.setBounds(0, 0, 1160, 915);

        getContentPane().add(contentLayeredPane);
        contentLayeredPane.setBounds(100, 0, 1160, 915);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeLabelMouseEntered

        DashboardHandler.navBarEnter(homeLabel);
        
    }//GEN-LAST:event_homeLabelMouseEntered

    private void homeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeLabelMouseExited
        
        if(selectedIndex != 0)
            DashboardHandler.navBarExit(homeLabel);
        
    }//GEN-LAST:event_homeLabelMouseExited

    private void bankLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bankLabelMouseReleased
        selectedIndex = 1;
        DashboardHandler.updateNavBarSelection(this.getLabelList(), this.getSideLabels(), this.getPanelList(), selectedIndex);
    }//GEN-LAST:event_bankLabelMouseReleased

    private void inventoryLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryLabelMouseReleased
        selectedIndex = 2;
        DashboardHandler.updateNavBarSelection(this.getLabelList(), this.getSideLabels(), this.getPanelList(), selectedIndex);
        client.sendMessageToServer("LoadInventory:flower");
    }//GEN-LAST:event_inventoryLabelMouseReleased

    private void homeLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeLabelMouseReleased
        selectedIndex = 0;
        DashboardHandler.updateNavBarSelection(this.getLabelList(), this.getSideLabels(), this.getPanelList(), selectedIndex);
        DashboardHandler.getPanelComponents(this.cloneUIPanel);
        
    }//GEN-LAST:event_homeLabelMouseReleased

    private void bankLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bankLabelMouseEntered
        DashboardHandler.navBarEnter(bankLabel);
    }//GEN-LAST:event_bankLabelMouseEntered

    private void bankLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bankLabelMouseExited
        if(selectedIndex != 1)
            DashboardHandler.navBarExit(bankLabel);
    }//GEN-LAST:event_bankLabelMouseExited

    private void inventoryLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryLabelMouseEntered
        DashboardHandler.navBarEnter(inventoryLabel);
    }//GEN-LAST:event_inventoryLabelMouseEntered

    private void inventoryLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryLabelMouseExited
        if(selectedIndex != 2)
            DashboardHandler.navBarExit(inventoryLabel);
    }//GEN-LAST:event_inventoryLabelMouseExited

    private void indoorLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_indoorLabelMouseEntered
        DashboardHandler.navBarEnter(indoorLabel);
    }//GEN-LAST:event_indoorLabelMouseEntered

    private void indoorLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_indoorLabelMouseExited
        if(selectedIndex != 3)
            DashboardHandler.navBarExit(indoorLabel);
    }//GEN-LAST:event_indoorLabelMouseExited

    private void outdoorLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outdoorLabelMouseEntered
        DashboardHandler.navBarEnter(outdoorLabel);
    }//GEN-LAST:event_outdoorLabelMouseEntered

    private void outdoorLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outdoorLabelMouseExited
        if(selectedIndex != 4)
            DashboardHandler.navBarExit(outdoorLabel);
    }//GEN-LAST:event_outdoorLabelMouseExited

    private void seedbankLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seedbankLabelMouseEntered
        DashboardHandler.navBarEnter(seedbankLabel);
    }//GEN-LAST:event_seedbankLabelMouseEntered

    private void seedbankLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seedbankLabelMouseExited
        if(selectedIndex != 5)
            DashboardHandler.navBarExit(seedbankLabel);
    }//GEN-LAST:event_seedbankLabelMouseExited

    private void phenotypesLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phenotypesLabelMouseEntered
        DashboardHandler.navBarEnter(phenotypesLabel);
    }//GEN-LAST:event_phenotypesLabelMouseEntered

    private void phenotypesLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phenotypesLabelMouseExited
        if(selectedIndex != 6)
            DashboardHandler.navBarExit(phenotypesLabel);
    }//GEN-LAST:event_phenotypesLabelMouseExited

    private void clonesLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clonesLabelMouseEntered
        DashboardHandler.navBarEnter(clonesLabel);
    }//GEN-LAST:event_clonesLabelMouseEntered

    private void clonesLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clonesLabelMouseExited
        if(selectedIndex != 7)
            DashboardHandler.navBarExit(clonesLabel);
    }//GEN-LAST:event_clonesLabelMouseExited

    private void indoorLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_indoorLabelMouseReleased
        selectedIndex = 3;
        DashboardHandler.updateNavBarSelection(this.getLabelList(), this.getSideLabels(), this.getPanelList(), selectedIndex);
        
        
        //client.sendMessageToServer("RequestData:PlantList:97");
     
        client.sendMessageToServer("RequestData:GrowRoomModule:29");

        //requestHandler.sendRequest("RequestData PlantList 97");
        
        /*this.indoorPlantListMapPanel.setVisible(false);
        this.currentRoom = new GrowRoom("97");
        currentRoom.getGrowRoomController().setPanels(this.getIndoorPanelList());
        this.requestHandler.sendRequest("RequestData PlantList 97");*/
        
        
        
        /*client.sendMessageToServer("RequestData PlantList 97");
        client.awaitMessageFromServer();
       
        this.currentRoom = new GrowRoom("97");
        currentRoom.getGrowRoomController().setPlantList(IndoorHandler.buildPlantList(client.getStreamList()));
        currentRoom.getGrowRoomController().setPanels(this.getIndoorPanelList());
        client.clearStreamList();
        
        
        
        //IndoorHandler indoorHandler = new IndoorHandler(client.getStreamList(),this.IndoorPlantSelectionPanel, this.IndoorRoomDetailsPanel, this.IndoorCalendarPanel);
        
        IndoorHandler.loadGrowRoom(this.indoorPlantListPanel);
       
        */
        
        
    }//GEN-LAST:event_indoorLabelMouseReleased

    private void outdoorLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outdoorLabelMouseReleased
        selectedIndex = 4;
        DashboardHandler.updateNavBarSelection(this.getLabelList(), this.getSideLabels(), this.getPanelList(), selectedIndex);
        
        
        
        this.phenoFinderPanel.setVisible(false);
    }//GEN-LAST:event_outdoorLabelMouseReleased

    private void seedbankLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seedbankLabelMouseReleased
        selectedIndex = 5;
        DashboardHandler.updateNavBarSelection(this.getLabelList(), this.getSideLabels(), this.getPanelList(), selectedIndex);
        client.sendMessageToServer("RequestData:Seeds");
        
        this.addPackPanel.setVisible(false);
        this.seedPackDisplayPanel.setVisible(true);
        this.addPackCheckPanel.setVisible(false);
        
        
    }//GEN-LAST:event_seedbankLabelMouseReleased

    private void phenotypesLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phenotypesLabelMouseReleased
        selectedIndex = 6;
        DashboardHandler.updateNavBarSelection(this.getLabelList(), this.getSideLabels(), this.getPanelList(), selectedIndex);
    }//GEN-LAST:event_phenotypesLabelMouseReleased

    private void clonesLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clonesLabelMouseReleased
        selectedIndex = 7;
        DashboardHandler.updateNavBarSelection(this.getLabelList(), this.getSideLabels(), this.getPanelList(), selectedIndex);
        this.phenoFinderPanel.setVisible(false);
        this.cloneEventPanel.setVisible(false);
        this.cloneSelectionPanel.setVisible(false);
        

        
        client.sendMessageToServer("RequestData Cuttings MachineOne");
        //client.awaitMessageFromServer();
        this.cuttingList = CuttingHandler.buildCuttingList(client.getStreamList());
        client.clearStreamList();
        CloningMachineHandler.cloneMachineSetup(cuttingList, this.getMachine2SlotList());
        
    }//GEN-LAST:event_clonesLabelMouseReleased

    private void settingsLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsLabelMouseEntered
        
        DashboardHandler.navBarEnter(settingsLabel);
    }//GEN-LAST:event_settingsLabelMouseEntered

    private void settingsLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsLabelMouseExited
        DashboardHandler.navBarExit(settingsLabel);
    }//GEN-LAST:event_settingsLabelMouseExited

    private void A1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A1MouseEntered

        this.A1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_A1MouseEntered

    private void A6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A6MouseEntered
        this.A6.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_A6MouseEntered

    private void D1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D1MouseEntered
        this.D1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_D1MouseEntered

    private void F6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F6MouseEntered
        this.F6.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_F6MouseEntered

    private void A1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A1MouseReleased
    
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
        
    }//GEN-LAST:event_A1MouseReleased

    private void F4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F4MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_F4MouseReleased

    private void phenoFinderSearchLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phenoFinderSearchLabelMouseReleased
        /*
        ArrayList<Phenotype> phenoSearchListReturn = new ArrayList();
        phenoSearchListReturn = DashboardHandler.phenoSearch(searchField.getText());
        DashboardHandler.resetPhenoSearchFields(this.getPhenoFinderDisplayLabels());
        
        if(phenoSearchListReturn.size() == 0)
        {
            System.out.println("Invalid whatever");
        }
        
        else if(phenoSearchListReturn.size() == 1)
        {
            DashboardHandler.searchDisplay(phenoSearchListReturn, this.getPhenoFinderDisplayLabels());
        }
        
        else if(phenoSearchListReturn.size() >= 2)
        {
            DashboardHandler.searchDisplay(phenoSearchListReturn, this.getPhenoFinderDisplayLabels());
        }
        
        
        //*/
    }//GEN-LAST:event_phenoFinderSearchLabelMouseReleased

    private void C1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C1MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_C1MouseReleased

    private void E6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E6MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_E6MouseReleased

    private void D4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D4MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_D4MouseReleased

    private void E4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E4MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_E4MouseReleased

    private void A6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A6MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_A6MouseReleased

    private void F5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F5MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_F5MouseReleased

    private void A2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A2MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_A2MouseReleased

    private void A3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A3MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_A3MouseReleased

    private void A4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A4MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_A4MouseReleased

    private void A5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A5MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_A5MouseReleased

    private void B1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B1MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_B1MouseReleased

    private void B2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B2MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_B2MouseReleased

    private void B3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B3MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_B3MouseReleased

    private void B4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B4MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_B4MouseReleased

    private void B5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B5MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_B5MouseReleased

    private void B6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B6MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_B6MouseReleased

    private void C2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C2MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_C2MouseReleased

    private void C3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C3MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_C3MouseReleased

    private void C4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C4MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_C4MouseReleased

    private void C5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C5MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_C5MouseReleased

    private void C6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C6MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_C6MouseReleased

    private void D1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D1MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_D1MouseReleased

    private void D2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D2MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_D2MouseReleased

    private void D3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D3MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_D3MouseReleased

    private void D5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D5MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_D5MouseReleased

    private void D6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D6MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_D6MouseReleased

    private void E1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E1MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_E1MouseReleased

    private void E2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E2MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_E2MouseReleased

    private void E3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E3MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_E3MouseReleased

    private void E5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E5MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_E5MouseReleased

    private void F1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F1MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_F1MouseReleased

    private void F2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F2MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_F2MouseReleased

    private void F3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F3MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_F3MouseReleased

    private void F6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F6MouseReleased
        selectedSlot = CloningMachineHandler.selectCloneSlot(evt.getComponent().getName(), this.cuttingList, this.cloneSelectionPanel, editSlotPanel, this.phenoFinderPanel, backgroundBlurLabel);
    }//GEN-LAST:event_F6MouseReleased

    private void jLabel41MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseReleased
        this.phenoFinderPanel.setVisible(false);
    }//GEN-LAST:event_jLabel41MouseReleased

    private void A2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A2MouseEntered
        this.A2.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_A2MouseEntered

    private void A3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A3MouseEntered
        this.A3.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_A3MouseEntered

    private void A4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A4MouseEntered
        this.A4.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_A4MouseEntered

    private void A5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_A5MouseEntered
        this.A5.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_A5MouseEntered

    private void B1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B1MouseEntered
        this.B1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_B1MouseEntered

    private void B2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B2MouseEntered
        this.B2.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_B2MouseEntered

    private void B3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B3MouseEntered
        this.B3.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_B3MouseEntered

    private void B4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B4MouseEntered
        this.B4.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_B4MouseEntered

    private void B5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B5MouseEntered
        this.B5.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_B5MouseEntered

    private void B6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_B6MouseEntered
        this.B6.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_B6MouseEntered

    private void C1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C1MouseEntered
        this.C1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_C1MouseEntered

    private void C2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C2MouseEntered
        this.C2.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_C2MouseEntered

    private void C3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C3MouseEntered
        this.C3.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_C3MouseEntered

    private void C4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C4MouseEntered
        this.C4.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_C4MouseEntered

    private void C5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C5MouseEntered
        this.C5.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_C5MouseEntered

    private void C6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C6MouseEntered
        this.C6.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_C6MouseEntered

    private void D2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D2MouseEntered
        this.D2.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_D2MouseEntered

    private void D3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D3MouseEntered
        this.D3.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_D3MouseEntered

    private void D4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D4MouseEntered
        this.D4.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_D4MouseEntered

    private void D5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D5MouseEntered
        this.D5.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_D5MouseEntered

    private void D6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_D6MouseEntered
        this.D6.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_D6MouseEntered

    private void E1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E1MouseEntered
        this.E1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_E1MouseEntered

    private void E2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E2MouseEntered
        this.E2.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_E2MouseEntered

    private void E3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E3MouseEntered
        this.E3.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_E3MouseEntered

    private void E4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E4MouseEntered
        this.E4.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_E4MouseEntered

    private void E5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E5MouseEntered
        this.E5.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_E5MouseEntered

    private void E6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_E6MouseEntered
        this.E6.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_E6MouseEntered

    private void F1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F1MouseEntered
        this.F1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_F1MouseEntered

    private void F2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F2MouseEntered
        this.F2.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_F2MouseEntered

    private void F3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F3MouseEntered
        this.F3.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_F3MouseEntered

    private void F4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F4MouseEntered
        this.F4.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_F4MouseEntered

    private void F5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_F5MouseEntered
        this.F5.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_F5MouseEntered

    private void settingsLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsLabelMouseReleased
        client.disconnectFromServer();
    }//GEN-LAST:event_settingsLabelMouseReleased

    private void addPackConfirmationTrueLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPackConfirmationTrueLabelMouseReleased
        //this.addPackCheckPanel.setVisible(false);
        this.addPackPanel.setEnabled(true);
        packQuantityTemp = Integer.parseInt(this.addPackQuantityCounterLabel.getText());
        
        client.sendMessageToServer("AddSeedPack:" + packQuantityTemp + ":" + addPackReturnString);
        
        
    }//GEN-LAST:event_addPackConfirmationTrueLabelMouseReleased

    private void submitPackButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitPackButtonMouseReleased
        
        //In future include builder for this variable
        this.addPackReturnString = 
                                "0," +
                                this.addPackCultivarField.getText() + "," +
                                this.addPackBreederField.getText() + "," +
                                this.addPackStockField.getText() + "," +
                                this.addPackReleaseField.getText() + "," +
                                this.addPackLineageField.getText() + "," +
                                this.addPackSeedTypeField.getText() + "," +
                                this.addPackFloweringTimeField.getText() + "," +
                                this.addPackTerpeneProfileField.getText() + "," +
                                this.addPackSizeField.getText() + "," +
                                this.addPackYieldField.getText() + "," +
                                this.addPackDescriptionArea.getText();
        
        //Loads display of add pack check panel
        this.addPackCheckPanel.setVisible(true);
    }//GEN-LAST:event_submitPackButtonMouseReleased

    private void backToPackDisplayLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backToPackDisplayLabelMouseReleased
        this.addPackPanel.setVisible(false);
        this.seedPackDisplayPanel.setVisible(true);
    }//GEN-LAST:event_backToPackDisplayLabelMouseReleased

    private void addPackAddQuanitityLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPackAddQuanitityLabelMouseReleased
        this.addPackSeedQuantity++;
        addPackQuantityCounterLabel.setText(String.valueOf(this.addPackSeedQuantity));
    }//GEN-LAST:event_addPackAddQuanitityLabelMouseReleased

    private void addPackSubQuanitityLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPackSubQuanitityLabelMouseReleased
        
        if(addPackSeedQuantity > 0)
        {
            this.addPackSeedQuantity--;
            addPackQuantityCounterLabel.setText(String.valueOf(this.addPackSeedQuantity));   
        }
        
    }//GEN-LAST:event_addPackSubQuanitityLabelMouseReleased

    private void addPackConfirmationFalseLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPackConfirmationFalseLabelMouseReleased
        this.addPackCheckPanel.setVisible(false);
        this.addPackPanel.setEnabled(true);
    }//GEN-LAST:event_addPackConfirmationFalseLabelMouseReleased

    private void addPackFilterPanelLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPackFilterPanelLabelMouseReleased
        this.seedbankFilterPanel.setVisible(false);
        this.addPackPanel.setVisible(true);
    }//GEN-LAST:event_addPackFilterPanelLabelMouseReleased

    private void addPackClearLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPackClearLabelMouseReleased
        SeedBankHandler.clearAddPackTextFields();
        
    }//GEN-LAST:event_addPackClearLabelMouseReleased

    private void helpAdminLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpAdminLabelMouseReleased
        
    }//GEN-LAST:event_helpAdminLabelMouseReleased

    private void inventoryItemNameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryItemNameFieldKeyReleased
       
    }//GEN-LAST:event_inventoryItemNameFieldKeyReleased

    private void inventoryItemDateFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryItemDateFieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_inventoryItemDateFieldKeyReleased

    private void inventoryItemBatchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryItemBatchFieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_inventoryItemBatchFieldKeyReleased

    private void inventoryItemWeightFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryItemWeightFieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_inventoryItemWeightFieldKeyReleased

    private void inventoryAddItemSubmitLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryAddItemSubmitLabelMouseReleased
        client.sendMessageToServer(InventoryController.buildNewItemString());
    }//GEN-LAST:event_inventoryAddItemSubmitLabelMouseReleased

    private void inventoryCuredSelectionLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryCuredSelectionLabelMouseReleased
        InventoryController.setSelectedItemStatus(1);
    }//GEN-LAST:event_inventoryCuredSelectionLabelMouseReleased

    private void inventoryCuringSelectionLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryCuringSelectionLabelMouseReleased
        InventoryController.setSelectedItemStatus(2);
    }//GEN-LAST:event_inventoryCuringSelectionLabelMouseReleased

    private void inventoryHangingSelectionLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryHangingSelectionLabelMouseReleased
        InventoryController.setSelectedItemStatus(3);
    }//GEN-LAST:event_inventoryHangingSelectionLabelMouseReleased

    private void inventoryTypeIndoorLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryTypeIndoorLabelMouseReleased
        InventoryController.setSelectedItemType(1);
    }//GEN-LAST:event_inventoryTypeIndoorLabelMouseReleased

    private void inventoryTypeOutdoorLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryTypeOutdoorLabelMouseReleased
        InventoryController.setSelectedItemType(2);
    }//GEN-LAST:event_inventoryTypeOutdoorLabelMouseReleased

    private void jLabel29MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseReleased
        InventoryController.loadAddItemPanel();
    }//GEN-LAST:event_jLabel29MouseReleased

    private void inventoryProfileDataLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryProfileDataLabelMouseReleased
        
        
    }//GEN-LAST:event_inventoryProfileDataLabelMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NotificationPanel notificationPanel = new NotificationPanel(this, NotificationPanel.Type.WARNING, NotificationPanel.Location.TOP_CENTER, "Warning Message");
        notificationPanel.showNotification();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        NotificationPanel notificationPanel = new NotificationPanel(this, NotificationPanel.Type.SUCCESS, NotificationPanel.Location.TOP_CENTER, "Success Message");
        notificationPanel.showNotification();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        NotificationPanel notificationPanel = new NotificationPanel(this, NotificationPanel.Type.INFO, NotificationPanel.Location.TOP_CENTER, "Info Message");
        notificationPanel.showNotification();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void pcSubmitLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcSubmitLabelMouseReleased
        String clientStr = "CreateExistingPlants:";
        clientStr += PlantCreationHandler.createPlantString();
        
        
        client.sendMessageToServer(clientStr);
    }//GEN-LAST:event_pcSubmitLabelMouseReleased

    private void pcSeedingLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcSeedingLabelMouseReleased
        PlantCreationHandler.setPlantStage("SEEDLING");
    }//GEN-LAST:event_pcSeedingLabelMouseReleased

    private void pcVegLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcVegLabelMouseReleased
        PlantCreationHandler.setPlantStage("VEGETATIVE");
    }//GEN-LAST:event_pcVegLabelMouseReleased

    private void pcFlowerLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcFlowerLabelMouseReleased
        PlantCreationHandler.setPlantStage("FLOWER");
    }//GEN-LAST:event_pcFlowerLabelMouseReleased

    private void jLabel42MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseReleased
        this.phenoFinderPanel.setVisible(true);
    }//GEN-LAST:event_jLabel42MouseReleased

    private void addCloneLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addCloneLabelMouseReleased

    }//GEN-LAST:event_addCloneLabelMouseReleased

    private void jLabel16MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseReleased
        this.editSlotPanel.setVisible(false);
    }//GEN-LAST:event_jLabel16MouseReleased

    private void searchIconLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchIconLabelMouseReleased
        /*  Phenotype searchPheno = new Phenotype();
        searchPheno = PlantDataManager.getPheno(Integer.parseInt(this.motherIdTextField.getText()));

        if(searchPheno.getPlantId() != 0)
        phenoLoaded = true;

        else
        phenoLoaded = false;

        if(phenoLoaded)
        {
            this.searchFeedbackLabel.setForeground(Color.green);
            this.searchFeedbackLabel.setText("Pheno loaded!");
            this.idLabel.setText(String.valueOf(searchPheno.getPlantId()));
            this.strainLabel.setText(searchPheno.getSeed().getCultivar());
            this.locationLabel.setText(String.valueOf(GrowRoom.getGrowRoom(searchPheno.getLocation())));
        }

        else
        {
            this.searchFeedbackLabel.setForeground(Color.red);
            this.searchFeedbackLabel.setText("Pheno not found.");
            this.idLabel.setText("");
            this.strainLabel.setText("");
            this.locationLabel.setText("");
        }

        /*
        try
        {
            searchPheno = PlantDataManager.getPheno(Integer.parseInt(this.motherIdTextField.getText()));

            phenoLoaded = true;
        }

        catch(Exception e)
        {

        }

        if(phenoLoaded)
        System.out.println("Pheno Loaded");
        this.idLabel.setText(String.valueOf(searchPheno.getPlantId()));
        this.strainLabel.setText(searchPheno.getStrain());
        this.locationLabel.setText(searchPheno.getLocation());
        */
    }//GEN-LAST:event_searchIconLabelMouseReleased

    private void jLabel56MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseReleased
        CloningMachineHandler.cloneEventDisplay(selectedSlot, cloneEventPanel);
    }//GEN-LAST:event_jLabel56MouseReleased

    private void jLabel39MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseReleased
        this.cloneStartupPanel.setVisible(false);
    }//GEN-LAST:event_jLabel39MouseReleased

    private void jLabel60MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel60MouseReleased
        //CloningMachineHandler.logCloneEvent(this.cloneEventPlantId.getText(), this.cloneEventTextArea.getText());
    }//GEN-LAST:event_jLabel60MouseReleased

    private void jLabel62MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel62MouseReleased
        this.cloneEventTextArea.setText("");
    }//GEN-LAST:event_jLabel62MouseReleased

    private void jLabel59MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel59MouseReleased
        this.cloneEventPanel.setVisible(false);
    }//GEN-LAST:event_jLabel59MouseReleased

    private void jLabel40MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseReleased
        this.phenoFinderPanel.setVisible(true);
    }//GEN-LAST:event_jLabel40MouseReleased

    private void viewCuttingMotherButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewCuttingMotherButton1MouseReleased
        this.cloneUIPanel.setVisible(false);

    }//GEN-LAST:event_viewCuttingMotherButton1MouseReleased

    private void CloneMachine2Slot34MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot34MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot34MouseReleased

    private void CloneMachine2Slot34MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot34MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot34MouseEntered

    private void CloneMachine2Slot27MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot27MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot27MouseReleased

    private void CloneMachine2Slot27MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot27MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot27MouseEntered

    private void CloneMachine2Slot20MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot20MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot20MouseReleased

    private void CloneMachine2Slot20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot20MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot20MouseEntered

    private void CloneMachine2Slot13MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot13MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot13MouseReleased

    private void CloneMachine2Slot13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot13MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot13MouseEntered

    private void CloneMachine2Slot6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot6MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot6MouseReleased

    private void CloneMachine2Slot6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot6MouseEntered

    private void CloneMachine2Slot35MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot35MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot35MouseReleased

    private void CloneMachine2Slot35MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot35MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot35MouseEntered

    private void CloneMachine2Slot33MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot33MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot33MouseReleased

    private void CloneMachine2Slot33MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot33MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot33MouseEntered

    private void CloneMachine2Slot26MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot26MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot26MouseReleased

    private void CloneMachine2Slot26MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot26MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot26MouseEntered

    private void CloneMachine2Slot19MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot19MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot19MouseReleased

    private void CloneMachine2Slot19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot19MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot19MouseEntered

    private void CloneMachine2Slot12MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot12MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot12MouseReleased

    private void CloneMachine2Slot12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot12MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot12MouseEntered

    private void CloneMachine2Slot5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot5MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot5MouseReleased

    private void CloneMachine2Slot5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot5MouseEntered

    private void CloneMachine2Slot28MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot28MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot28MouseReleased

    private void CloneMachine2Slot28MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot28MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot28MouseEntered

    private void CloneMachine2Slot32MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot32MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot32MouseReleased

    private void CloneMachine2Slot32MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot32MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot32MouseEntered

    private void CloneMachine2Slot25MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot25MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot25MouseReleased

    private void CloneMachine2Slot25MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot25MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot25MouseEntered

    private void CloneMachine2Slot18MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot18MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot18MouseReleased

    private void CloneMachine2Slot18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot18MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot18MouseEntered

    private void CloneMachine2Slot11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot11MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot11MouseReleased

    private void CloneMachine2Slot11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot11MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot11MouseEntered

    private void CloneMachine2Slot4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot4MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot4MouseReleased

    private void CloneMachine2Slot4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot4MouseEntered

    private void CloneMachine2Slot21MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot21MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot21MouseReleased

    private void CloneMachine2Slot21MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot21MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot21MouseEntered

    private void CloneMachine2Slot31MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot31MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot31MouseReleased

    private void CloneMachine2Slot31MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot31MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot31MouseEntered

    private void CloneMachine2Slot24MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot24MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot24MouseReleased

    private void CloneMachine2Slot24MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot24MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot24MouseEntered

    private void CloneMachine2Slot17MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot17MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot17MouseReleased

    private void CloneMachine2Slot17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot17MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot17MouseEntered

    private void CloneMachine2Slot10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot10MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot10MouseReleased

    private void CloneMachine2Slot10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot10MouseEntered

    private void CloneMachine2Slot3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot3MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot3MouseReleased

    private void CloneMachine2Slot3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot3MouseEntered

    private void CloneMachine2Slot14MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot14MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot14MouseReleased

    private void CloneMachine2Slot14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot14MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot14MouseEntered

    private void CloneMachine2Slot30MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot30MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot30MouseReleased

    private void CloneMachine2Slot30MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot30MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot30MouseEntered

    private void CloneMachine2Slot23MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot23MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot23MouseReleased

    private void CloneMachine2Slot23MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot23MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot23MouseEntered

    private void CloneMachine2Slot16MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot16MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot16MouseReleased

    private void CloneMachine2Slot16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot16MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot16MouseEntered

    private void CloneMachine2Slot9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot9MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot9MouseReleased

    private void CloneMachine2Slot9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot9MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot9MouseEntered

    private void CloneMachine2Slot2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot2MouseReleased

    private void CloneMachine2Slot2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot2MouseEntered

    private void CloneMachine2Slot7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot7MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot7MouseReleased

    private void CloneMachine2Slot7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot7MouseEntered

    private void CloneMachine2Slot29MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot29MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot29MouseReleased

    private void CloneMachine2Slot29MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot29MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot29MouseEntered

    private void CloneMachine2Slot22MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot22MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot22MouseReleased

    private void CloneMachine2Slot22MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot22MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot22MouseEntered

    private void CloneMachine2Slot15MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot15MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot15MouseReleased

    private void CloneMachine2Slot15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot15MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot15MouseEntered

    private void CloneMachine2Slot8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot8MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot8MouseReleased

    private void CloneMachine2Slot8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot8MouseEntered

    private void CloneMachine2Slot1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot1MouseReleased

    private void CloneMachine2Slot1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloneMachine2Slot1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CloneMachine2Slot1MouseEntered

    private void IndoorModulePanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IndoorModulePanelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_IndoorModulePanelMouseEntered

    private static JFrame datFram;
    
    
    
    
    public static JFrame getDatFram()
    {
        return datFram;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    datFram = new NewDashboard();
                    datFram.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(NewDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel A1;
    private javax.swing.JLabel A2;
    private javax.swing.JLabel A3;
    private javax.swing.JLabel A4;
    private javax.swing.JLabel A5;
    private javax.swing.JLabel A6;
    private javax.swing.JLabel B1;
    private javax.swing.JLabel B2;
    private javax.swing.JLabel B3;
    private javax.swing.JLabel B4;
    private javax.swing.JLabel B5;
    private javax.swing.JLabel B6;
    private javax.swing.JLabel C1;
    private javax.swing.JLabel C2;
    private javax.swing.JLabel C3;
    private javax.swing.JLabel C4;
    private javax.swing.JLabel C5;
    private javax.swing.JLabel C6;
    private javax.swing.JPanel CloneMachine2Panel;
    private javax.swing.JLabel CloneMachine2Slot1;
    private javax.swing.JLabel CloneMachine2Slot10;
    private javax.swing.JLabel CloneMachine2Slot11;
    private javax.swing.JLabel CloneMachine2Slot12;
    private javax.swing.JLabel CloneMachine2Slot13;
    private javax.swing.JLabel CloneMachine2Slot14;
    private javax.swing.JLabel CloneMachine2Slot15;
    private javax.swing.JLabel CloneMachine2Slot16;
    private javax.swing.JLabel CloneMachine2Slot17;
    private javax.swing.JLabel CloneMachine2Slot18;
    private javax.swing.JLabel CloneMachine2Slot19;
    private javax.swing.JLabel CloneMachine2Slot2;
    private javax.swing.JLabel CloneMachine2Slot20;
    private javax.swing.JLabel CloneMachine2Slot21;
    private javax.swing.JLabel CloneMachine2Slot22;
    private javax.swing.JLabel CloneMachine2Slot23;
    private javax.swing.JLabel CloneMachine2Slot24;
    private javax.swing.JLabel CloneMachine2Slot25;
    private javax.swing.JLabel CloneMachine2Slot26;
    private javax.swing.JLabel CloneMachine2Slot27;
    private javax.swing.JLabel CloneMachine2Slot28;
    private javax.swing.JLabel CloneMachine2Slot29;
    private javax.swing.JLabel CloneMachine2Slot3;
    private javax.swing.JLabel CloneMachine2Slot30;
    private javax.swing.JLabel CloneMachine2Slot31;
    private javax.swing.JLabel CloneMachine2Slot32;
    private javax.swing.JLabel CloneMachine2Slot33;
    private javax.swing.JLabel CloneMachine2Slot34;
    private javax.swing.JLabel CloneMachine2Slot35;
    private javax.swing.JLabel CloneMachine2Slot4;
    private javax.swing.JLabel CloneMachine2Slot5;
    private javax.swing.JLabel CloneMachine2Slot6;
    private javax.swing.JLabel CloneMachine2Slot7;
    private javax.swing.JLabel CloneMachine2Slot8;
    private javax.swing.JLabel CloneMachine2Slot9;
    private javax.swing.JLabel D1;
    private javax.swing.JLabel D2;
    private javax.swing.JLabel D3;
    private javax.swing.JLabel D4;
    private javax.swing.JLabel D5;
    private javax.swing.JLabel D6;
    private javax.swing.JLabel E1;
    private javax.swing.JLabel E2;
    private javax.swing.JLabel E3;
    private javax.swing.JLabel E4;
    private javax.swing.JLabel E5;
    private javax.swing.JLabel E6;
    private javax.swing.JLabel F1;
    private javax.swing.JLabel F2;
    private javax.swing.JLabel F3;
    private javax.swing.JLabel F4;
    private javax.swing.JLabel F5;
    private javax.swing.JLabel F6;
    private javax.swing.JPanel IndoorModulePanel;
    private javax.swing.JPanel SelectedGrowRoomPanel;
    private javax.swing.JLabel addCloneLabel;
    private javax.swing.JLabel addPackAddQuanitityLabel;
    private javax.swing.JTextField addPackBreederField;
    private javax.swing.JPanel addPackCheckPanel;
    private javax.swing.JLabel addPackCheckPanelBackground;
    private javax.swing.JLabel addPackClearLabel;
    private javax.swing.JLabel addPackConfirmationFalseLabel;
    private javax.swing.JLabel addPackConfirmationTrueLabel;
    private javax.swing.JTextField addPackCultivarField;
    private javax.swing.JTextArea addPackDescriptionArea;
    private javax.swing.JLabel addPackFilterPanelLabel;
    private javax.swing.JTextField addPackFloweringTimeField;
    private javax.swing.JTextField addPackLineageField;
    private javax.swing.JPanel addPackPanel;
    private javax.swing.JLabel addPackQuantityCounterLabel;
    private javax.swing.JTextField addPackReleaseField;
    private javax.swing.JTextField addPackSeedTypeField;
    private javax.swing.JTextField addPackSizeField;
    private javax.swing.JTextField addPackStockField;
    private javax.swing.JLabel addPackSubQuanitityLabel;
    private javax.swing.JTextField addPackTerpeneProfileField;
    private javax.swing.JTextField addPackYieldField;
    private javax.swing.JLabel backToPackDisplayLabel;
    private javax.swing.JLabel backgroundBlurLabel;
    private javax.swing.JLabel bankLabel;
    private javax.swing.JPanel bankPanel;
    private javax.swing.JLabel bankSideLabel;
    private javax.swing.JLabel cloneEventBackground;
    private javax.swing.JPanel cloneEventPanel;
    private javax.swing.JLabel cloneEventPlantId;
    private javax.swing.JLabel cloneEventPlantTag;
    private javax.swing.JTextArea cloneEventTextArea;
    private javax.swing.JPanel cloneMachineDashboardPanel;
    private javax.swing.JPanel cloneMachineSidePanel;
    private javax.swing.JPanel cloneMachineSlot1;
    private javax.swing.JPanel cloneMachineSlot2;
    private javax.swing.JPanel cloneMachineSlot3;
    private javax.swing.JPanel cloneMachineSlot4;
    private javax.swing.JPanel cloneMachineSlot5;
    private javax.swing.JPanel cloneMachineSlot6;
    private javax.swing.JPanel cloneMachineSlot7;
    private javax.swing.JPanel cloneMachineSlot8;
    private javax.swing.JPanel cloneMachineSlot9;
    private javax.swing.JLabel cloneSelectionCutDateLabel;
    private javax.swing.JLabel cloneSelectionMotherPlantIdLabel;
    private javax.swing.JPanel cloneSelectionPanel;
    private javax.swing.JLabel cloneSelectionPanelBG;
    private javax.swing.JLabel cloneSelectionPanelCutDateText;
    private javax.swing.JLabel cloneSelectionPanelMotherIdText;
    private javax.swing.JLabel cloneSelectionPanelPlantIdText;
    private javax.swing.JLabel cloneSelectionPanelSlotLabel;
    private javax.swing.JLabel cloneSelectionPanelStrain;
    private javax.swing.JLabel cloneSelectionPlantIdLabel;
    private javax.swing.JPanel cloneStartupPanel;
    private javax.swing.JPanel cloneUIPanel;
    private javax.swing.JPanel cloneUIPanel1;
    private javax.swing.JLabel clonesLabel;
    private javax.swing.JPanel clonesPanel;
    private javax.swing.JLabel clonesSideLabel;
    private javax.swing.JLayeredPane contentLayeredPane;
    private javax.swing.JPanel customIndoorListPanel;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JLabel dragLabel;
    private javax.swing.JLabel editSlotBackground;
    private javax.swing.JPanel editSlotPanel;
    private javax.swing.JPanel emptyCuttingSlotPanel;
    private javax.swing.JPanel environmentalPanel;
    private javax.swing.JLabel growRoomLightToolIcon;
    private javax.swing.JLabel growRoomOverviewLabel;
    private javax.swing.JPanel growRoomOverviewPanel;
    private javax.swing.JLabel growRoomPlantListPanel;
    private javax.swing.JPanel growRoomToolNavBarPanel;
    private javax.swing.JPanel growRoomToolPanel;
    private javax.swing.JLabel growRoomToolPlantStatIcon;
    private javax.swing.JLabel growRoomToolWateringIcon;
    private javax.swing.JPanel growroomPlantToolPanel;
    private javax.swing.JLabel grwoRoomToolPlantIcon;
    private javax.swing.JLabel helpAdminLabel;
    private javax.swing.JLabel homeLabel;
    private javax.swing.JLabel homeSideLabel;
    private javax.swing.JLabel idCell1;
    private javax.swing.JLabel idCell10;
    private javax.swing.JLabel idCell2;
    private javax.swing.JLabel idCell3;
    private javax.swing.JLabel idCell4;
    private javax.swing.JLabel idCell5;
    private javax.swing.JLabel idCell6;
    private javax.swing.JLabel idCell7;
    private javax.swing.JLabel idCell8;
    private javax.swing.JLabel idCell9;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel idTitleLabel;
    private javax.swing.JLabel indoorLabel;
    private javax.swing.JPanel indoorPanel;
    private javax.swing.JLabel indoorSideLabel;
    private javax.swing.JLabel inventoryAddItemBackground;
    private javax.swing.JPanel inventoryAddItemPanel;
    private javax.swing.JLabel inventoryAddItemSubmitLabel;
    private javax.swing.JLabel inventoryCuredSelectionLabel;
    private javax.swing.JLabel inventoryCuringSelectionLabel;
    private javax.swing.JPanel inventoryDisplayPanel;
    private javax.swing.JLabel inventoryHangingSelectionLabel;
    private javax.swing.JPanel inventoryHomePanel;
    private javax.swing.JTextField inventoryItemBatchField;
    private javax.swing.JTextField inventoryItemDateField;
    private javax.swing.JTextField inventoryItemNameField;
    private javax.swing.JTextField inventoryItemWeightField;
    private javax.swing.JLabel inventoryLabel;
    private javax.swing.JPanel inventoryListContainer;
    private javax.swing.JPanel inventoryListPanel;
    private javax.swing.JLabel inventoryListPanelBackground;
    private javax.swing.JPanel inventoryPanel;
    private javax.swing.JLabel inventoryProfileDataLabel;
    private javax.swing.JLabel inventorySelectionBatchIdLabel;
    private javax.swing.JLabel inventorySelectionIdLabel;
    private javax.swing.JLabel inventorySelectionItemNameLabel;
    private javax.swing.JPanel inventorySelectionPanel;
    private javax.swing.JLabel inventorySelectionPanelBackground;
    private javax.swing.JLabel inventorySideLabel;
    private javax.swing.JPanel inventorySidePanel;
    private javax.swing.JLabel inventoryTypeIndoorLabel;
    private javax.swing.JLabel inventoryTypeOutdoorLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel locationCell1;
    private javax.swing.JLabel locationCell10;
    private javax.swing.JLabel locationCell2;
    private javax.swing.JLabel locationCell3;
    private javax.swing.JLabel locationCell4;
    private javax.swing.JLabel locationCell5;
    private javax.swing.JLabel locationCell6;
    private javax.swing.JLabel locationCell7;
    private javax.swing.JLabel locationCell8;
    private javax.swing.JLabel locationCell9;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JLabel locationTitleLab;
    private javax.swing.JTextField motherIdTextField;
    private javax.swing.JLabel motherplantsSideLabel;
    private javax.swing.JLabel nLabel;
    private javax.swing.JLabel nLabel1;
    private javax.swing.JLabel nLabel2;
    private javax.swing.JLayeredPane navigationPane;
    private javax.swing.JLabel outdoorLabel;
    private javax.swing.JPanel outdoorPanel;
    private javax.swing.JLabel outdoorSideLabel;
    private javax.swing.JLabel packBreederLabel;
    private javax.swing.JLabel packCultivarLabel;
    private javax.swing.JLabel packFloweringTimeLabel;
    private javax.swing.JLabel packIdLabel;
    private javax.swing.JLabel packImageLabel;
    private javax.swing.JLabel packLineageLabel;
    private javax.swing.JLabel packReleaseLabel;
    private javax.swing.JLabel packSeedTypeLabel;
    private javax.swing.JLabel packSizeLabel;
    private javax.swing.JLabel packTerpeneLabel;
    private javax.swing.JLabel packYieldLabel;
    private javax.swing.JLabel pcBgLabel;
    private javax.swing.JTextField pcCultivarText;
    private javax.swing.JLabel pcFlowerLabel;
    private javax.swing.JTextField pcGrowIdText;
    private javax.swing.JTextField pcPlantNumberText;
    private javax.swing.JLabel pcSeedingLabel;
    private javax.swing.JLabel pcSubmitLabel;
    private javax.swing.JLabel pcVegLabel;
    private javax.swing.JPanel phenoFinderPanel;
    private javax.swing.JLabel phenoFinderSearchLabel;
    private javax.swing.JLabel phenotypesLabel;
    private javax.swing.JPanel phenotypesPanel;
    private javax.swing.JPanel plantCreationPanel;
    private javax.swing.JLabel roomSpecBgLabel;
    private javax.swing.JLabel searchFeedbackLabel;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchIconLabel;
    private javax.swing.JPanel seedBankCellPanel;
    private javax.swing.JPanel seedBankSidePanel;
    private javax.swing.JLabel seedPackDisplayBackground;
    private javax.swing.JLabel seedPackDisplayBackground1;
    private javax.swing.JPanel seedPackDisplayPanel;
    private javax.swing.JLabel seedStockLabel;
    private javax.swing.JLabel seedbankFilterBackgroundLabel;
    private javax.swing.JPanel seedbankFilterPanel;
    private javax.swing.JLabel seedbankLabel;
    private javax.swing.JPanel seedbankPanel;
    private javax.swing.JLabel seedbankSideLabel;
    private javax.swing.JPanel selectedCuttingPanel;
    private javax.swing.JLabel settingsLabel;
    private javax.swing.JLabel strainCell1;
    private javax.swing.JLabel strainCell10;
    private javax.swing.JLabel strainCell2;
    private javax.swing.JLabel strainCell3;
    private javax.swing.JLabel strainCell4;
    private javax.swing.JLabel strainCell5;
    private javax.swing.JLabel strainCell6;
    private javax.swing.JLabel strainCell7;
    private javax.swing.JLabel strainCell8;
    private javax.swing.JLabel strainCell9;
    private javax.swing.JLabel strainLabel;
    private javax.swing.JLabel strainTitleLabel;
    private javax.swing.JLabel submitPackButton;
    private javax.swing.JPanel supportPanel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JLabel viewCuttingMotherButton;
    private javax.swing.JLabel viewCuttingMotherButton1;
    // End of variables declaration//GEN-END:variables
}
