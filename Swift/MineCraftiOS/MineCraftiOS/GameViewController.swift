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

class GameViewController: UIViewController {

    var cameraNode:SCNNode?
    public func readJSON()->Void{
    
    }
    public func writeJSON()->Void{
    
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let scene = setupScene()
        addBlock(to: scene, with: "WoodCubeA.jpg", at: SCNVector3Make(10, 10, 10))
    }
    func setupScene()->SCNScene{
        // create a new scene
        let scene = SCNScene()
        
        // create and add a camera to the scene
        let cameraNode = SCNNode()
        cameraNode.camera = SCNCamera()
        scene.rootNode.addChildNode(cameraNode)
        
        // place the camera
        cameraNode.position = SCNVector3(x: 20, y: 20, z: 25)
        self.cameraNode = cameraNode
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
        return scene
    }
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
    func addBlock(to scene:SCNScene,with imageName:String,at position:SCNVector3){
        let block = SCNNode()
        block.position = position
        block.geometry = SCNBox.init(width: 5, height: 5, length: 5, chamferRadius: 0)
        block.geometry?.firstMaterial?.diffuse.contents = imageName
        block.geometry?.firstMaterial?.diffuse.mipFilter = .linear
        block.physicsBody = SCNPhysicsBody.dynamic()
        scene.rootNode.addChildNode(block)
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
