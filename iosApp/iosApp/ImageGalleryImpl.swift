import UIKit
import Photos
import SharedSDK


class ImageGalleryImpl:NSObject {

    let imageManager = PHImageManager.default()
    var fetchResult: PHFetchResult<PHAsset>?
    var selectedImages = [(UIImage, URL)]()


    func getImagess(completion: @escaping ([(UIImage, URL)]) -> Void) {
        let status = PHPhotoLibrary.authorizationStatus()
        if status == .authorized {
            self.fetchImages(completion: completion)
        } else if status == .denied || status == .restricted {
            completion([])
        } else {
            PHPhotoLibrary.requestAuthorization { (newStatus) in
                if newStatus == .authorized {
                    self.fetchImages(completion: completion)
                } else {
                    completion([])
                }
            }
        }
    }

    private func fetchImages(completion: @escaping ([(UIImage, URL)]) -> Void) {
        let fetchOptions = PHFetchOptions()
        fetchOptions.sortDescriptors = [NSSortDescriptor(key: "creationDate", ascending: false)]
        fetchResult = PHAsset.fetchAssets(with: .image, options: fetchOptions)

        guard let fetchResult = fetchResult else {
            completion([])
            return
        }

        let requestOptions = PHImageRequestOptions()
        requestOptions.deliveryMode = .fastFormat
        requestOptions.isSynchronous = true

        for i in 0..<fetchResult.count {
            imageManager.requestImage(for: fetchResult.object(at: i), targetSize: CGSize(width: 200, height: 200), contentMode: .aspectFill, options: requestOptions) { (image, info) in
                if let image = image, let url = info?["PHImageFileURLKey"] as? URL {
                    self.selectedImages.append((image, url))
                }
            }
        }

        completion(selectedImages)
    }
}