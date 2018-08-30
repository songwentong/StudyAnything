//
//  ViewController.swift
//  CPUFireAll
//
//  Created by 宋文通 on 2018/8/30.
//  Copyright © 2018年 宋文通. All rights reserved.
//

import UIKit
import CoreLocation
class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        for _ in 0..<12345{
            createQueue()
        }
        
    }
    func createQueue() -> Void {
        for _ in 0..<1000000000 {
            self.animationFuncation()
        }
    }
    func animationFuncation() -> Void {
        let a = Double(arc4random())
        let r = a.squareRoot().squareRoot().squareRoot()
        print("a:\(a) r:\(r)")
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.init(uptimeNanoseconds: 1)) {
            self.animationFuncation()
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

