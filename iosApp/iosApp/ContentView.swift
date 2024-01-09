import UIKit
import SwiftUI
import SharedSDK
import WebKit

typealias MyLambda = (String?) -> Void

let myLambda: MyLambda = { param in
    print("myLambda called")
    print(param ?? "nil")
    MyObservableObject.shared.url = param
}

struct ComposeView: UIViewControllerRepresentable {

    func makeUIViewController(context: Context) -> UIViewController {
        AvoidDispose(MainKt.MainViewController())
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    @EnvironmentObject var globalObservable: MyObservableObject


    var body: some View {
        GeometryReader { geometry in
            VStack(spacing:0){
                ComposeView()
            }
        }
    }
}