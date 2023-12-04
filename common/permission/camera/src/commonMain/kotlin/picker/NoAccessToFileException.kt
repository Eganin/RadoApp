package picker

class NoAccessToFileException(path: String) : RuntimeException("no access to $path")