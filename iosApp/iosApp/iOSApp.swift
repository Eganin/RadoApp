import SwiftUI

@main
struct iOSApp: App {

	var body: some Scene {
		WindowGroup {
            ContentView().environmentObject(MyObservableObject.shared)
		}
	}
}


class MyObservableObject: ObservableObject {
    static let shared = MyObservableObject()
    @Published var url: String? = nil

    // Other properties and methods go here
}