//
//  ViewController.swift
//  WTKitTest
//
//  Created by SongWentong on 19/12/2016.
//  Copyright © 2016 songwentong. All rights reserved.
//

import UIKit
import WTKit
class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let task = WTKit.dataTask(with: "https://www.apple.com")
        task.completionHandler = {(data,response,error)in
            print("handle response/error")
        }
        task.stringHandler = {(string,error)in
            print("handle string/error")
        }
        task.jsonHandler = {(a,b)in
            print("handle json/error")
        }
        task.progressHandler = {(n1,n2)in
            print("handle progress")
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

