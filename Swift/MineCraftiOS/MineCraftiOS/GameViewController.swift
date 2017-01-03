//
//  GameViewController.swift
//  MineCraftiOS
//
//  Created by SongWentong on 30/12/2016.
//  Copyright © 2016 songwentong. All rights reserved.
//

import UIKit
import QuartzCore
import SceneKit
/*
 下一步,建立四面的墙壁
 
 */
class GameViewController: UIViewController {

    @IBOutlet var scnView: WTGameView!
    var cameraNode:SCNNode?
    var blockNode:SCNNode?
    var label:UILabel?
    var tapGesture:UITapGestureRecognizer?
    var panGesture:UIPanGestureRecognizer?
    public func readJSON()->Void{
    
    }
    public func writeJSON()->Void{
    
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupScene()
        blockNode = addBlock(with: "WoodCubeA", at: SCNVector3Make(10, 10, 100))
//        addGestures()
    }
    func addGestures(){
        panGesture?.delegate = self
        panGesture = UIPanGestureRecognizer.init(target: self, action: #selector(GameViewController.didrecieveGesture(_:)))
        self.view.addGestureRecognizer(panGesture!)
    }
    func setUpCameraTimer(){

//         _ = Timer.scheduledTimer(timeInterval: 0.1, target: self, selector: #selector(GameViewController.timerMethod(timer:)), userInfo: nil, repeats: true)
    }
    
    //添加四面的墙壁
    func addWalls(scene:SCNScene){
        let box = SCNBox.init(width: 400, height: 100, length: 4, chamferRadius: 0)
        box.firstMaterial?.diffuse.contents = "wall.jpg"
        box.firstMaterial?.diffuse.contentsTransform = SCNMatrix4Mult(SCNMatrix4MakeScale(24, 2, 1), SCNMatrix4MakeTranslation(0, 1, 0));
        box.firstMaterial?.diffuse.wrapS = .repeat
        box.firstMaterial?.diffuse.wrapT = .mirror
        box.firstMaterial?.isDoubleSided = false
        
        var wall = SCNNode.init(geometry: box)
        wall.position = SCNVector3Make(0, 50, -92)
        wall.physicsBody = SCNPhysicsBody.static()
        wall.castsShadow = false
        scene.rootNode.addChildNode(wall);
        
        
        wall = wall.clone()
        wall.position = SCNVector3Make(-202, 50, 0)
        wall.physicsBody = SCNPhysicsBody.static()
        wall.rotation = SCNVector4Make(0, 1, 0, Float(M_PI_2))
        scene.rootNode.addChildNode(wall);
        wall = wall.clone()
        wall.position = SCNVector3Make(202, 50, 0);
        wall.rotation = SCNVector4Make(0, 1, 0, Float(-M_PI_2))
        wall.physicsBody = SCNPhysicsBody.static()
        scene.rootNode.addChildNode(wall);
        wall = wall.clone()
        wall.position = SCNVector3Make(0, 50, 200)
        wall.rotation = SCNVector4Make(0, 1, 0, Float(M_PI))
        wall.physicsBody = SCNPhysicsBody.static()
        scene.rootNode.addChildNode(wall);
    }
    
    //创建环境
    func setupScene(){
        
//        self.scnView.pointOfView = SCNNode()
        // create a new scene
        let scene = SCNScene()
        
        // create and add a camera to the scene
        let cameraNode = SCNNode()
        cameraNode.camera = SCNCamera()
        cameraNode.camera?.zFar = 500;
        cameraNode.position = SCNVector3Make(10, 10, 20);
//        cameraNode.eulerAngles = SCNVector3Make(3, 0.6, 3)
//        cameraNode.rotation  = SCNVector4Make(1, 1, 1, Float(-M_PI*0.75));
        scene.rootNode.addChildNode(cameraNode)
        
        // place the camera
        self.cameraNode = cameraNode
        self.scnView.pointOfView = cameraNode
        self.scnView.delegate = self;
        
        // create and add a light to the scene
        let lightNode = SCNNode()
        lightNode.light = SCNLight()
        lightNode.light!.type = .omni
        lightNode.position = SCNVector3(x: 10, y: 10, z: 5)
        scene.rootNode.addChildNode(lightNode)
        
        // create and add an ambient light to the scene
        let ambientLightNode = SCNNode()
        ambientLightNode.light = SCNLight()
        ambientLightNode.light!.type = .ambient
        ambientLightNode.light!.color = UIColor.darkGray
        scene.rootNode.addChildNode(ambientLightNode)
        
        // retrieve the ship node
        //        let ship = scene.rootNode.childNode(withName: "ship", recursively: true)!
        
        // animate the 3d object
        //        ship.runAction(SCNAction.repeatForever(SCNAction.rotateBy(x: 0, y: 2, z: 0, duration: 1)))
        
        // retrieve the SCNView
        let scnView = self.view as! SCNView
        
        // set the scene to the view
        scnView.scene = scene
        
        // allows the user to manipulate the camera
        scnView.allowsCameraControl = true
        
        // show statistics such as fps and timing information
        scnView.showsStatistics = true
        
        // configure the view
        scnView.backgroundColor = UIColor.black
        
        // add a tap gesture recognizer
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(handleTap(_:)))
        scnView.addGestureRecognizer(tapGesture)
        setupGround()
        label = UILabel()
        label?.frame = CGRect(x: 0, y: 0, width: 1000, height: 25)
        label?.textColor = UIColor.red
        label?.text = "camera"
        self.view.addSubview(label!)
        addWalls(scene: scene)
    }
    
    //建个地板
    func setupGround(){
        //floor
        let floor = SCNNode()
        floor.geometry = SCNFloor()
        floor.geometry?.firstMaterial?.diffuse.contents = "wood.png"
        floor.geometry?.firstMaterial?.diffuse.contentsTransform = SCNMatrix4MakeScale(2, 2, 1)
        floor.geometry?.firstMaterial?.locksAmbientWithDiffuse = true
        let body = SCNPhysicsBody.static()
        floor.physicsBody = body
        if let view:SCNView = self.view as! SCNView? {
            view.scene?.rootNode.addChildNode(floor)
        }
    }
    func addBlock(with imageName:String,at position:SCNVector3)->SCNNode?{
        
        if let scene:SCNScene = self.scnView.scene {
            let block = SCNNode()
            block.position = position
            block.geometry = SCNBox.init(width: 5, height: 5, length: 5, chamferRadius: 0)
            block.geometry?.firstMaterial?.diffuse.contents = imageName
            block.geometry?.firstMaterial?.diffuse.mipFilter = .linear
            block.physicsBody = SCNPhysicsBody.dynamic()
            block.eulerAngles = SCNVector3.init(M_PI, 0, 0)
            scene.rootNode.addChildNode(block)
            return block
        }
        return nil
    }
    func handleTap(_ gestureRecognize: UIGestureRecognizer) {
        // retrieve the SCNView
        let scnView = self.view as! SCNView
        
        // check what nodes are tapped
        let p = gestureRecognize.location(in: scnView)
        let hitResults = scnView.hitTest(p, options: [:])
        // check that we clicked on at least one object
        if hitResults.count > 0 {
            // retrieved the first clicked object
            let result: AnyObject = hitResults[0]
            
            // get its material
            let material = result.node!.geometry!.firstMaterial!
            
            // highlight it
            SCNTransaction.begin()
            SCNTransaction.animationDuration = 0.5
            
            // on completion - unhighlight
            SCNTransaction.completionBlock = {
                SCNTransaction.begin()
                SCNTransaction.animationDuration = 0.5
                
                material.emission.contents = UIColor.black
                
                SCNTransaction.commit()
            }
            
            material.emission.contents = UIColor.red
            
            SCNTransaction.commit()
        }
    }
    
    override var shouldAutorotate: Bool {
        return true
    }
    
    override var prefersStatusBarHidden: Bool {
        return true
    }
    
    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
        if UIDevice.current.userInterfaceIdiom == .phone {
            return .allButUpsideDown
        } else {
            return .all
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Release any cached data, images, etc that aren't in use.
    }

}
extension GameViewController:UIGestureRecognizerDelegate{
    func didrecieveGesture(_ geture:UIGestureRecognizer){
        if tapGesture == geture {
            
        }else if panGesture == panGesture {
            
        }
    }
}
extension GameViewController:SCNSceneRendererDelegate{
    public func renderer(_ renderer: SCNSceneRenderer, updateAtTime time: TimeInterval){
        if let block = blockNode {
//            cameraNode?.position = block.position
        }
        if let scnView:SCNView = self.view as! SCNView? {
//            label?.text = "camera: \(scnView.pointOfView?.eulerAngles)"
            //            print("camera: \(scnView.pointOfView?.eulerAngles)")
        }
        
        
    }
}
